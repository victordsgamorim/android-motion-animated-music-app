package com.victor.musicapp.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.victor.musicapp.data.api.SpotifyArtistService
import com.victor.musicapp.data.api.SpotifyArtistTrackService
import com.victor.musicapp.data.api.SpotifyTokenService
import com.victor.musicapp.data.api.response.SpotifyListOfArtistsResponse
import com.victor.musicapp.data.api.response.SpotifyApiResponse
import com.victor.musicapp.data.api.response.SpotifyTokenResponse
import com.victor.musicapp.data.database.dao.SpotifyArtistTrackDao
import com.victor.musicapp.data.database.dao.TrackDao
import com.victor.musicapp.data.database.entities.SpotifyArtistTrackRequest
import com.victor.musicapp.data.database.entities.Track
import com.victor.musicapp.data.util.*
import com.victor.musicapp.data.util.SharedPreferencesConstants.GENERATED_TOKEN_TIME
import com.victor.musicapp.data.util.SharedPreferencesConstants.LONG_DEFAULT
import com.victor.musicapp.data.util.SpotifyConstants.OAUTH_TOKEN_ACCESS_MAP
import com.victor.musicapp.data.util.SpotifyConstants.OAUTH_TOKEN_HEADER
import com.victor.musicapp.data.util.SpotifyConstants.STATUS_ERROR
import com.victor.musicapp.domain.model.OAuthToken
import com.victor.musicapp.domain.model.mapper.SpotifyArtistTrackMapper
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.OAuthTokenEvent
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.SearchTokenDatabaseEvent
import com.victor.musicapp.presenter.ui.main.state.MainViewState
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val service: SpotifyArtistTrackService,
    private val tokenService: SpotifyTokenService,
    private val prefEditor: SharedPreferences.Editor,
    private val pref: SharedPreferences,
    private val spotifyArtistTrackDao: SpotifyArtistTrackDao,
    private val trackDao: TrackDao,
    private val artistService: SpotifyArtistService
) {

    private var repositoryJob: Job? = null

    private fun getGeneratedTokenTime(): Long {
        return pref.getLong(GENERATED_TOKEN_TIME, LONG_DEFAULT)
    }

    fun checkTokenIntegrity(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<Void, MainViewState>() {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<Void>) {
                // dont do anything!
            }

            override fun responseCall(): LiveData<GenericApiResponse<Void>> {
                return AbsentLiveData.create()
            }

            override suspend fun loadCachedData() {
                if (getGeneratedTokenTime() == LONG_DEFAULT || getGeneratedTokenTime().isExpired()) {
                    return onCompleteResponse(
                        DataState.data(
                            MainViewState(
                                event = OAuthTokenEvent(
                                    OAuthToken(
                                        OAUTH_TOKEN_HEADER,
                                        OAUTH_TOKEN_ACCESS_MAP
                                    )
                                )
                            )
                        )
                    )
                } else {
                    onCompleteResponse(
                        DataState.data(MainViewState(event = SearchTokenDatabaseEvent))
                    )
                }
            }

            override fun setJob(job: Job) {
                addNewJob(job)
            }

        }.asLiveData
    }

    fun generateNewToken(oAuthToken: OAuthToken): LiveData<DataState<MainViewState>> {
        return object :
            NetworkBoundResource<SpotifyTokenResponse, MainViewState>(
                isNetWorkRequested = true,
                isLoadingRequested = true
            ) {

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<SpotifyTokenResponse>) {

                /**clean table if there is token
                 * the reason of that is because I dont know want to store more than one token in
                 * the database **/
                val rowCount = spotifyArtistTrackDao.getRowCount()
                if (rowCount > 0) {
                    spotifyArtistTrackDao.cleanTable()
                }


                val result = response.body
                val artistTrack =
                    SpotifyArtistTrackRequest(
                        authToken = result.accessToken!!,
                        authTokenType = result.tokenType!!
                    )

                /** add SpotifyArtistTrackRequest to database if there is no token*/
                spotifyArtistTrackDao.addAuthToken(spotifyArtistTrack = artistTrack)

                /** adding time in millis  into shared preferences in order
                 * to check its token if it is expired or not, **/
                prefEditor.putLong(GENERATED_TOKEN_TIME, artistTrack.tokenTime)
                prefEditor.apply()

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
            NetworkBoundResource<SpotifyApiResponse, MainViewState>(
                isNetWorkRequested = true,
                isLoadingRequested = true
            ) {
            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<SpotifyApiResponse>) {

                val result = response.body

                /**If the result return an error response a message of the error will be shown
                 * indicating what was it.*/
                if (response.body.error?.status == STATUS_ERROR) {
                    return onErrorResponse(result.error?.message!!)
                }

                /**saving the track to the database */
                val trackResult = response.body.result!!
                val track =
                    Track(
                        items = trackResult.items,
                        tokenId = spotifyArtistTrackRequest.authToken
                    )
                trackDao.addTrack(track)

                /**search artist from api */
                val token =
                    "${spotifyArtistTrackRequest.authTokenType} ${spotifyArtistTrackRequest.authToken}"


                /**add list of artist into database*/


                onCompleteResponse(
                    dataState = DataState.data(
                        data = MainViewState(track = track, token = token)
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

    fun getCurrentToken(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<Void, MainViewState>(isLoadingRequested = true) {

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

                val time = getGeneratedTokenTime()
                val spotifyArtistTrack = spotifyArtistTrackDao.getToken(time)

                val track = trackDao.getTrackByTheToken(spotifyArtistTrack.authToken)

                withContext(Main) {
                    onCompleteResponse(
                        dataState = DataState.data(
                            data = MainViewState(track = track)
                        )
                    )
                }
            }

        }.asLiveData
    }

    fun getArtists(
        token: String,
        track: Track
    ): LiveData<DataState<MainViewState>> {
        val artist = track.items[0].artists

        val mutableListOf = mutableListOf<String>()

        for (i in artist) {
            mutableListOf.add(i.id)
        }

        return object :
            NetworkBoundResource<SpotifyListOfArtistsResponse, MainViewState>(isNetWorkRequested = true) {

            override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<SpotifyListOfArtistsResponse>) {
                /**list of artist to database */
            }

            override fun responseCall(): LiveData<GenericApiResponse<SpotifyListOfArtistsResponse>> {
                return artistService.getArtist(token, mutableListOf)
            }

            override fun setJob(job: Job) {
                addNewJob(job)
            }

            override suspend fun loadCachedData() {
                // do not do anything
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