package ir.ebcom.gifapplication.ui.viewmodel

import ir.ebcom.gifapplication.data.consts.FETCH_GIF_TIME_IN_MILL
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.ebcom.gifapplication.data.ResponseState
import ir.ebcom.gifapplication.data.enums.PageAction
import ir.ebcom.gifapplication.data.localmodel.GifModel
import ir.ebcom.gifapplication.data.localmodel.SearchResults
import ir.ebcom.gifapplication.data.repository.ApiRepository
import ir.ebcom.gifapplication.ui.base.BaseViewModel
import ir.ebcom.gifapplication.util.toSingleEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeGifViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : BaseViewModel(){

    var job: Job? =null


    private val _gif =MutableLiveData<ResponseState<GifModel>>()
    val gif: LiveData<ResponseState<GifModel>> =_gif.toSingleEvent()
    fun startToFetchRandomGif(){
        job =viewModelScope.launch {
            while (true){
                apiRepository.getRandomGif().flowOn(Dispatchers.IO).collect {
                    _gif.postValue(it)
                }
                delay(FETCH_GIF_TIME_IN_MILL)
            }
        }
    }


    fun cancelFetchingGifJob(){
        job?.cancel()
    }



    private var _searchResult =MutableLiveData<ResponseState<SearchResults>>()
    val searchResult: LiveData<ResponseState<SearchResults>> =_searchResult.toSingleEvent()


    fun search(query: String){
        if (query.isEmpty()){
            return
        }
        viewModelScope.launch {
            apiRepository.searchForGifs(query).flowOn(Dispatchers.IO).collect {
                _searchResult.postValue(it)
            }
        }
    }


    private var _action =MutableLiveData(PageAction.RANDOM)
    val pageAction: LiveData<PageAction> =_action.toSingleEvent()

    fun setAction(action: PageAction) {
        _action.value =action
    }


    fun getAction() = _action.value

    override fun onCleared() {
        Log.d("HomeGifFragment", "onCleared: ")
        cancelFetchingGifJob()
        super.onCleared()
        job =null
    }









}