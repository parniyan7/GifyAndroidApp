package ir.ebcom.gifapplication.data.remotemodel

import com.google.gson.annotations.SerializedName


data class RandomGifResponse(
    @SerializedName("data")
    val gifData: GifData? = null,
    @SerializedName("meta")
    val meta: Any? = null
)

data class GifData(
    @SerializedName("bitly_gif_url")
    val bitlyGifUrl: String? = null,
    @SerializedName("bitly_url")
    val bitlyUrl: String? = null,
    @SerializedName("content_url")
    val contentUrl: String? = null,
    @SerializedName("embed_url")
    val embedUrl: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("import_datetime")
    val importDatetime: String? = null,
    @SerializedName("is_sticker")
    val isSticker: Int? = null,
    @SerializedName("rating")
    val rating: String? = null,
    @SerializedName("slug")
    val slug: String? = null,
    @SerializedName("source")
    val source: String? = null,
    @SerializedName("source_post_url")
    val sourcePostUrl: String? = null,
    @SerializedName("source_tld")
    val sourceTld: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("trending_datetime")
    val trendingDatetime: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("images")
    val images: OriginalImage
)

data class OriginalImage(
    @SerializedName("original")
    val originalUrl: OriginalUrl?
)

data class OriginalUrl(
    @SerializedName("url")
    val url: String? = null
)
