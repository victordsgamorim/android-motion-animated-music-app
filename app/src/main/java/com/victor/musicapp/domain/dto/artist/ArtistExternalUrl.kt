package com.victor.musicapp.domain.dto.artist

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArtistExternalUrl(
    @SerializedName("spotify")
    @Expose
    val spotify: String
) : Parcelable
