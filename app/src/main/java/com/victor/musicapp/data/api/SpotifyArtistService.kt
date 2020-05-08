package com.victor.musicapp.data.api

import androidx.lifecycle.LiveData
import com.victor.musicapp.data.api.response.SpotifyListOfArtistsResponse
import com.victor.musicapp.data.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyArtistService {

    @GET("artists/")
    fun getArtist(
        @Header("Authorization") header: String,
        @Query("ids") id: List<String>
    ): LiveData<GenericApiResponse<SpotifyListOfArtistsResponse>>

}