package com.victor.musicapp.data.repository

import androidx.lifecycle.LiveData
import com.victor.musicapp.data.api.SpotifyArtistTrackService
import com.victor.musicapp.data.api.SpotifyTokenService
import com.victor.musicapp.data.util.ApiSuccessResponse
import com.victor.musicapp.data.util.DataState
import com.victor.musicapp.data.util.GenericApiResponse
import com.victor.musicapp.domain.dto.SpotifyApiResponse
import com.victor.musicapp.domain.dto.SpotifyTokenResponse
import com.victor.musicapp.domain.model.OAuthToken
import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest
import com.victor.musicapp.presenter.ui.main.state.MainViewState
import kotlinx.coroutines.Job
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val service: SpotifyArtistTrackService,
    private val tokenService: SpotifyTokenService
) {

    private var repositoryJob: Job? = null

    fun generateNewToken(oAuthToken: OAuthToken): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<SpotifyTokenResponse, MainViewState>() {

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<SpotifyTokenResponse>) {
                onCompleteResponse(
                    dataState = DataState.data(
                        data = MainViewState(spotifyTokenResponse = response.body)
                    )
                )
            }

            override fun responseCall(): LiveData<GenericApiResponse<SpotifyTokenResponse>> {
                return tokenService.generateNewToken(
                    oAuthToken.oauthToken,
                    oAuthToken.oauthTokenAccessMap
                )
            }

            override fun setJob(job: Job) {
                addNewJob(job)
            }

        }.asLiveData
    }


    fun getTrackResponse(spotifyArtistTrackRequest: SpotifyArtistTrackRequest): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<SpotifyApiResponse, MainViewState>() {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<SpotifyApiResponse>) {
                onCompleteResponse(
                    dataState = DataState.data(
                        data = MainViewState(spotifyApiResponse = response.body)
                    )
                )
            }

            override fun responseCall(): LiveData<GenericApiResponse<SpotifyApiResponse>> {
                return service.getTrack(
                    spotifyArtistTrackRequest.tokenHeaderMap,
                    spotifyArtistTrackRequest.queryMapArtistMap
                )
            }

            override fun setJob(job: Job) {
                addNewJob(job)
            }
        }.asLiveData
    }

    private fun addNewJob(job: Job) {
        cancelJob()
        repositoryJob = job
    }

    fun cancelJob() {
        repositoryJob?.cancel()
    }
}