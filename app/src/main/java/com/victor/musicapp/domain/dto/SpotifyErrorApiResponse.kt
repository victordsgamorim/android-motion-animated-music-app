package com.victor.musicapp.domain.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SpotifyErrorApiResponse(
    @SerializedName("status")
    @Expose
    val status: Int,

    @SerializedName("message")
    @Expose
    val message: String
)