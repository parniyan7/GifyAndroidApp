package ir.ebcom.gifapplication.data

import java.lang.Exception

data class LocalException(
    val throwable: Throwable,
    val code: Int =-1
) :Exception()
