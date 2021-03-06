package com.victor.musicapp.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class SpotifyErrorApiResponse(
    @SerializedName("status")
    @Expose
    val status: Int,

    @SerializedName("message")
    @Expose
    val message: String
) : Parcelable