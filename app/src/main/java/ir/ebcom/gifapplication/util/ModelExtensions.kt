package ir.ebcom.gifapplication.util

import ir.ebcom.gifapplication.data.enums.RatingSystem
import ir.ebcom.gifapplication.data.localmodel.GifModel
import ir.ebcom.gifapplication.data.localmodel.SearchResults
import ir.ebcom.gifapplication.data.remotemodel.RandomGifResponse
import ir.ebcom.gifapplication.data.remotemodel.SearchGifResponse

fun RandomGifResponse.toRandomGifModel() =GifModel(
    id = gifData?.id ?: "",
    url = gifData?.images?.originalUrl?.url ?: "",
    shortUrl = gifData?.bitlyGifUrl ?: "",
    title = gifData?.title ?: "",
    rating = getRating(gifData?.rating ?: "")
)



fun getRating(rating: String): RatingSystem {
    return when(rating){
        "g" -> RatingSystem.G
        "pg" -> RatingSystem.PG
        "pg-13" -> RatingSystem.PG_13
        "r" -> RatingSystem.R
        else -> RatingSystem.NOT_DEFINED
    }
}




fun SearchGifResponse.toSearchResult() =SearchResults(
    searchData?.map {
        GifModel(
            id = it.id?: "",
            url = it.images.originalImage.url ?: "",
            shortUrl = it.bitlyGifUrl ?: "",
            title = it.title ?: "",
            rating = getRating(it.rating ?: ""),
            previewUrl = it.images.previewUrl.url
        )
    }?.toList() ?: arrayListOf()
)