package ir.ebcom.gifapplication.data.consts
import ir.ebcom.gifapplication.data.enums.RatingSystem

const val UNKNOWN_EXCEPTION = 1
const val UNKNOWN_HOST_EXCEPTION = 2
const val UNKNOWN_HTTP_EXCEPTION = 3
const val IO_EXCEPTION = 4
const val TIMEOUT_EXCEPTION = 5
const val SOCKET_TIMEOUT_EXCEPTION = 5

const val SEARCH_DEBOUNCE_IN_MILL = 500L
const val FETCH_GIF_TIME_IN_MILL = 10000L

fun getRatingType(ratingSystem: RatingSystem) = when(ratingSystem) {
    RatingSystem.G -> "G"
    RatingSystem.PG -> "PG"
    RatingSystem.PG_13 -> "PG-13"
    RatingSystem.R -> "+17"
    else -> "Not Defined"
}
