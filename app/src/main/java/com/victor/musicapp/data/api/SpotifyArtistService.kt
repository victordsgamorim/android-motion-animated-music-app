package com.victor.musicapp.data.api

import androidx.lifecycle.LiveData
import com.victor.musicapp.data.api.response.SpotifyArtistResponse
import com.victor.musicapp.data.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface SpotifyArtistService {


    @GET("artists/{id}")
    fun getArtist(
        @Header("Authorization") header: String,
        @Path("id") id: String
    ): LiveData<GenericApiResponse<SpotifyArtistResponse>>
}