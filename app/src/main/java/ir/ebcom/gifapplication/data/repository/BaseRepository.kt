package ir.ebcom.gifapplication.data.repository

import ir.ebcom.gifapplication.data.consts.IO_EXCEPTION
import ir.ebcom.gifapplication.data.consts.SOCKET_TIMEOUT_EXCEPTION
import ir.ebcom.gifapplication.data.consts.TIMEOUT_EXCEPTION
import ir.ebcom.gifapplication.data.consts.UNKNOWN_EXCEPTION
import ir.ebcom.gifapplication.data.consts.UNKNOWN_HOST_EXCEPTION
import ir.ebcom.gifapplication.data.LocalException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

open class BaseRepository {
    protected fun <T> getFailedResponse(response: Response<T>): LocalException {
        return errorMapper(HttpException(response))
    }

    protected fun errorMapper(exception: Exception): LocalException{
        return when(exception) {
            is HttpException -> {
                LocalException(exception,exception.response()?.code() ?: UNKNOWN_EXCEPTION)
            }
            is UnknownHostException -> {
                LocalException(exception, UNKNOWN_HOST_EXCEPTION)
            }
            is IOException -> {
                LocalException(exception, IO_EXCEPTION)
            }
            is TimeoutException -> {
                LocalException(exception, TIMEOUT_EXCEPTION)
            }
            is SocketTimeoutException -> {
                LocalException(exception, SOCKET_TIMEOUT_EXCEPTION)
            }
            else -> {
                LocalException(exception, UNKNOWN_EXCEPTION)
            }
        }
    }
}