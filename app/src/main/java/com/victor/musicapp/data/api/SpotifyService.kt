package com.victor.musicapp.data.api

import androidx.lifecycle.LiveData
import com.victor.musicapp.domain.dto.SpotifyApiResponse
import com.victor.musicapp.data.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Query

interface SpotifyService {

    @GET("search?")
    fun getTrack(
        @HeaderMap headers: Map<String, String>,
        @Query("q") artist: String,
        @Query("type") type: String,
        @Query("limit") limit: Int
    ): LiveData<GenericApiResponse<SpotifyApiResponse>>


}