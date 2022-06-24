package ir.ebcom.gifapplication.util

/**
 * Copyright 2019 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.annotation.MainThread
import androidx.collection.ArraySet
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

enum class LiveEventConfig {
    /**
     * Supports multi-observers on all cases the same.
     */
    Normal,

    /**
     * Prefer the first observer when user emit the event, register observer, then the `onStart`
     * get called. In this case the _first observer_ will receive the _last event_.
     *
     * This scenario is specially useful when you want to emit the event in the `init` of
     * `ViewModel`, and expect the first observer receive it after `onStart`.
     */
    PreferFirstObserver
}

open class LiveEvent<T>(
    private val config: LiveEventConfig = LiveEventConfig.Normal
) : MediatorLiveData<T>() {

    private val observers = ArraySet<ObserverWrapper<in T>>()
    private var hasValueWithoutFirstObserver: Boolean = false

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        observers.find { it.observer === observer }?.let { _ -> // existing
            return
        }
        val wrapper = ObserverWrapper(observer)
        if (hasValueWithoutFirstObserver) {
            hasValueWithoutFirstObserver = false
            wrapper.newValue()
        }
        observers.add(wrapper)
        super.observe(owner, wrapper)
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        observers.find { it.observer === observer }?.let { _ -> // existing
            return
        }
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observeForever(wrapper)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        if (observer is ObserverWrapper && observers.remove(observer)) {
            super.removeObserver(observer)
            return
        }
        val iterator = observers.iterator()
        while (iterator.hasNext()) {
            val wrapper = iterator.next()
            if (wrapper.observer == observer) {
                iterator.remove()
                super.removeObserver(wrapper)
                break
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        if (config == LiveEventConfig.PreferFirstObserver &&
            observers.isEmpty()
        ) {
            hasValueWithoutFirstObserver = true
        }
        observers.forEach { it.newValue() }
        super.setValue(t)
    }

    private class ObserverWrapper<T>(val observer: Observer<T>) : Observer<T> {

        private var pending = false

        override fun onChanged(t: T?) {
            if (pending) {
                pending = false
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending = true
        }
    }
}

fun <T> LiveData<T>.toSingleEvent(): LiveData<T> {
    val result = LiveEvent<T>()
    result.addSource(this) {
        result.value = it
    }
    return result
}