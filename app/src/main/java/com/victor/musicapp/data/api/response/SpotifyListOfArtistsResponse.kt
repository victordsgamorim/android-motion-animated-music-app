package com.victor.musicapp.data.api.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.victor.musicapp.domain.dto.Artist

data class SpotifyListOfArtistsResponse(
    @SerializedName("error")
    @Expose
    val error: SpotifyErrorApiResponse,

    @SerializedName("artists")
    @Expose
    val artists: List<Artist>
) {

}