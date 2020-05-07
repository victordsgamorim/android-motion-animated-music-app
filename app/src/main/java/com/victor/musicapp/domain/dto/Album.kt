package com.victor.musicapp.domain.dto

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("album_type")
    @Expose
    val album_type: String,

    @SerializedName("artists")
    @Expose
    val artists: List<Artist>,

    @SerializedName("images")
    @Expose
    val images: List<AlbumImage>,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("release_date")
    @Expose
    val release_date: String,

    @SerializedName("type")
    @Expose
    val type: String

) : Parcelable