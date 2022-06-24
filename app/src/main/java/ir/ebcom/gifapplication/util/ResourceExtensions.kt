package ir.ebcom.gifapplication.util

import androidx.fragment.app.Fragment
import ir.ebcom.gifapplication.R
import ir.ebcom.gifapplication.data.LocalException
import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLHandshakeException

fun Fragment.getErrorMessage(localException: LocalException?) : String {
    return localException?.let {
        return when (localException.throwable) {
            is HttpException -> {
                getHttpErrorMessage(localException.throwable.code())
            }
            is SecurityException -> {
                getString(R.string.permission_denied)
            }
            is UnknownHostException -> {
                getString(R.string.unresolve_host)
            }
            is NullPointerException -> {
                getString(R.string.something_bad_happened)
            }
            is TimeoutException -> {
                getString(R.string.timeout)
            }
            is SSLHandshakeException -> {
                getString(R.string.ssl_handshake)
            }
            else -> getString(R.string.unknown_error)
        }
    }?: kotlin.run {
        getString(R.string.unknown_error)
    }
}





private fun Fragment.getHttpErrorMessage(httpCode: Int) :String{
    return when(httpCode){
        403 -> getString(R.string.error_403)
        404 -> getString(R.string.error_404)
        else -> getString(R.string.error_not_defined)

    }
}




fun String.isWhiteSpace (): Boolean {
    this.map {
        if (it.isWhitespace()){
            return true
        }
    }
    return false
}