package com.victor.musicapp.domain.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpotifyApiResponse(
    @SerializedName("error")
    @Expose
    var error: SpotifyErrorApiResponse? = null,

    @SerializedName("tracks")
    @Expose
    var result: Track? = null
) {

}


