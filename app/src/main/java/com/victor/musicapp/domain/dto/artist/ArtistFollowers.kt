package com.victor.musicapp.domain.dto.artist

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArtistFollowers(

    @SerializedName("href")
    @Expose
    val href: String,

    @SerializedName("total")
    @Expose
    val total: Long
) : Parcelable
