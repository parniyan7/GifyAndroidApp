package ir.ebcom.gifapplication.util

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun EditText.getQueryTextChangeStateFlow() : StateFlow<String> {
    val query= MutableStateFlow("")
    doAfterTextChanged {
        query.value=it.toString()
    }
    return query
}