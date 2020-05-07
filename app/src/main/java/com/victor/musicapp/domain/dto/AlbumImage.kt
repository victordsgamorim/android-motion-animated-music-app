package com.victor.musicapp.domain.dto

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumImage(
    @SerializedName("width")
    @Expose
    val width: Int,

    @SerializedName("height")
    @Expose
    val height: Int,

    @SerializedName("url")
    @Expose
    val url: String
) : Parcelable
