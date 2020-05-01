package com.victor.musicapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.victor.musicapp.data.api.SpotifyService
import com.victor.musicapp.data.api.SpotifyTokenService
import com.victor.musicapp.data.util.ApiEmptyResponse
import com.victor.musicapp.data.util.ApiErrorResponse
import com.victor.musicapp.data.util.ApiSuccessResponse
import com.victor.musicapp.data.util.SpotifyConstants.ALBUM_LIMIT
import com.victor.musicapp.data.util.SpotifyConstants.AUTH_TOKEN_ACCESS_MAP
import com.victor.musicapp.data.util.SpotifyConstants.AUTH_TOKEN_HEADER
import com.victor.musicapp.data.util.SpotifyConstants.SPOTIFY_ARTIST
import com.victor.musicapp.data.util.SpotifyConstants.SPOTIFY_TYPE
import com.victor.musicapp.domain.dto.SpotifyApiResponse
import com.victor.musicapp.domain.dto.SpotifyArtistTrackRequest
import com.victor.musicapp.domain.dto.SpotifyTokenResponse
import com.victor.musicapp.domain.model.Band
import com.victor.musicapp.presenter.ui.main.state.MainViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val service: SpotifyService,
    private val tokenService: SpotifyTokenService
) {

    fun getTrackResponse(spotifyArtistTrackRequest: SpotifyArtistTrackRequest): LiveData<MainViewState> {
        val result = MediatorLiveData<MainViewState>()

        CoroutineScope(IO).launch {

            withContext(Main) {

                result.addSource(
                    service.getTrack(
                        spotifyArtistTrackRequest.tokenHeaderMap,
                        SPOTIFY_ARTIST,
                        SPOTIFY_TYPE,
                        ALBUM_LIMIT
                    )
                )
                { response ->
                    when (response) {
                        is ApiSuccessResponse -> {
                            result.value = MainViewState(response.body)
                        }
                        is ApiEmptyResponse -> {
                        }
                        is ApiErrorResponse -> {
                            Log.e(
                                "TRACK", "${response.errorMessage}"
                            )
                        }
                    }

                }
            }
        }


        return result
    }

    fun getNewToken(): LiveData<SpotifyTokenResponse> {
        val result = MediatorLiveData<SpotifyTokenResponse>()

        CoroutineScope(IO).launch {
            withContext(Main) {
                result.addSource(
                    tokenService.generateNewToken(AUTH_TOKEN_HEADER, AUTH_TOKEN_ACCESS_MAP)
                ) { response ->
                    when (response) {
                        is ApiSuccessResponse -> {
                            result.value = response.body
                        }
                        is ApiEmptyResponse -> {
                        }
                        is ApiErrorResponse -> {
                            Log.e("TOKEN", "${response.errorMessage}")
                        }
                    }

                }
            }
        }

        return result
    }
}