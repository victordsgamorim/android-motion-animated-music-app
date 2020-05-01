package com.victor.musicapp.data.api

import androidx.lifecycle.LiveData
import com.victor.musicapp.data.util.GenericApiResponse
import com.victor.musicapp.domain.dto.SpotifyTokenResponse
import retrofit2.http.*

interface SpotifyTokenService {


    @POST("token")
    @FormUrlEncoded
    fun generateNewToken(
        @Header("Authorization") header: String,
        @FieldMap map: Map<String, String>
    ): LiveData<GenericApiResponse<SpotifyTokenResponse>>
}