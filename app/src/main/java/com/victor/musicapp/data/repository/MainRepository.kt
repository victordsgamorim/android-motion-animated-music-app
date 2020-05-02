package com.victor.musicapp.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import com.victor.musicapp.data.api.SpotifyArtistTrackService
import com.victor.musicapp.data.api.SpotifyTokenService
import com.victor.musicapp.data.api.response.SpotifyApiResponse
import com.victor.musicapp.data.api.response.SpotifyTokenResponse
import com.victor.musicapp.data.database.dao.SpotifyArtistTrackDao
import com.victor.musicapp.data.util.AbsentLiveData
import com.victor.musicapp.data.util.ApiSuccessResponse
import com.victor.musicapp.data.util.DataState
import com.victor.musicapp.data.util.GenericApiResponse
import com.victor.musicapp.data.util.SharedPreferencesConstants.LONG_DEFAULT
import com.victor.musicapp.data.util.SharedPreferencesConstants.GENERATED_TOKEN_ID
import com.victor.musicapp.data.util.SharedPreferencesConstants.GENERATED_TOKEN_TIME
import com.victor.musicapp.domain.model.OAuthToken
import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest
import com.victor.musicapp.domain.model.mapper.SpotifyArtistTrackMapper
import com.victor.musicapp.presenter.ui.main.state.MainViewState
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val service: SpotifyArtistTrackService,
    private val tokenService: SpotifyTokenService,
    private val preftEditor: SharedPreferences.Editor,
    private val artistTrackDao: SpotifyArtistTrackDao
) {

    private var repositoryJob: Job? = null

    fun generateNewToken(oAuthToken: OAuthToken): LiveData<DataState<MainViewState>> {
        return object :
            NetworkBoundResource<SpotifyTokenResponse, MainViewState>(isNetWorkRequested = true) {

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<SpotifyTokenResponse>) {
                val result = response.body
                val artistTrack = SpotifyArtistTrackRequest(
                    authToken = result.accessToken!!,
                    authTokenType = result.tokenType!!
                )

                /** add SpotifyArtistTrackRequest to database if there is no token*/
                val id = artistTrackDao.addAuthToken(spotifyArtistTrack = artistTrack)

                /** adding time in millis  into shared preferences in order
                 * to check its token if it is expired or not, **/
                preftEditor.putLong(GENERATED_TOKEN_TIME, oAuthToken.tokenTime)


                /**add long id to shared preferences*/
                preftEditor.putLong(GENERATED_TOKEN_ID, id)
                preftEditor.apply()

                /**response body being returned to Mediator Live Data inside the abstract class */
                onCompleteResponse(
                    dataState = DataState.data(
                        data = MainViewState(spotifyArtistTrackRequest = artistTrack)
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

            override suspend fun loadCachedData() {
                // do nothing!
            }

        }.asLiveData
    }


    fun getTrackResponse(spotifyArtistTrackRequest: SpotifyArtistTrackRequest): LiveData<DataState<MainViewState>> {
        val mapper = SpotifyArtistTrackMapper(spotifyArtistTrackRequest)

        return object :
            NetworkBoundResource<SpotifyApiResponse, MainViewState>(isNetWorkRequested = true) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<SpotifyApiResponse>) {
                onCompleteResponse(
                    dataState = DataState.data(
                        data = MainViewState(spotifyApiResponse = response.body)
                    )
                )
            }

            override fun responseCall(): LiveData<GenericApiResponse<SpotifyApiResponse>> {
                return service.getTrack(mapper.tokenHeaderMap, mapper.queryMapArtistMap)
            }

            override fun setJob(job: Job) {
                addNewJob(job)
            }

            override suspend fun loadCachedData() {
                //do nothing!
            }
        }.asLiveData
    }

    fun getCurrentToken(id: Long): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<Void, MainViewState>() {

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<Void>) {
                // do nothing!
            }

            override fun responseCall(): LiveData<GenericApiResponse<Void>> {
                return AbsentLiveData.create()
            }

            override fun setJob(job: Job) {
                addNewJob(job)
            }

            override suspend fun loadCachedData() {
                val artistTrack = artistTrackDao.getToken(id)
                withContext(Main) {
                    onCompleteResponse(
                        dataState = DataState.data(
                            data = MainViewState(spotifyArtistTrackRequest = artistTrack)
                        )
                    )
                }
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