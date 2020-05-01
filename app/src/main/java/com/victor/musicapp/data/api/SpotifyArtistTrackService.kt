package com.victor.musicapp.data.api

import androidx.lifecycle.LiveData
import com.victor.musicapp.domain.dto.SpotifyApiResponse
import com.victor.musicapp.data.util.GenericApiResponse
import retrofit2.http.*

interface SpotifyArtistTrackService {

    @GET("search?")
    fun getTrack(
        @HeaderMap headers: Map<String, String>,
        @QueryMap map: Map<String, String>
    ): LiveData<GenericApiResponse<SpotifyApiResponse>>


}