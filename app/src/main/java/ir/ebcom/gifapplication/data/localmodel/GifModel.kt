package ir.ebcom.gifapplication.data.localmodel

import android.os.Parcelable
import ir.ebcom.gifapplication.data.enums.RatingSystem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GifModel(
    val id:String,
    val url: String,
    val shortUrl: String,
    val title: String,
    val rating: RatingSystem,
    val previewUrl : String?=""
):Parcelable