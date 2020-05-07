package com.victor.musicapp.domain.dto.artist

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArtistImages(
    @SerializedName("height")
    @Expose
    val height: Long,

    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("width")
    @Expose
    val width: Long

) : Parcelable

