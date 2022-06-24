package ir.ebcom.gifapplication.data

sealed class ResponseState<out T>(
    val data: T? =null,
    val localException: LocalException?=null
){
    class Success<T>(data: T?=null): ResponseState<T>(data)
    class Error <T> (localException: LocalException,data: T?=null) : ResponseState<T> (data, localException)
    object Loading: ResponseState<Nothing>()
}