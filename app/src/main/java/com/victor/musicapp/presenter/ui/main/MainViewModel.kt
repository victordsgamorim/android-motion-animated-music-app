package com.victor.musicapp.presenter.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.victor.musicapp.data.repository.MainRepository
import com.victor.musicapp.data.util.DataState
import com.victor.musicapp.data.api.response.SpotifyApiResponse
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.*
import com.victor.musicapp.presenter.ui.main.state.MainViewState
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _stateEvent = MutableLiveData<MainStateEvent>()
    private val _viewState = MutableLiveData<MainViewState>()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    /**return the data triggered by the state event*/
    val dataState: LiveData<DataState<MainViewState>> =
        switchMap(_stateEvent) { stateEvent ->
            when (stateEvent) {
                is SpotifyArtistTrackRequestEvent -> {
                    repository.getTrackResponse(stateEvent.spotifyArtistTrackRequest)
                }
                is OAuthTokenEvent -> {
                    repository.generateNewToken(stateEvent.oauthToken)
                }
                is SearchTokenDatabaseEvent -> {
                    repository.getCurrentToken(stateEvent.id)
                }
            }
        }

    /**
    add new event in order to act as a trigger to start
    the search of some data from api or internal database*/

    fun addStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }

    /**
    create new instance of view state by adding the found data state in repository
    the result of adding the new instance of view state is to update the UI view */
    fun setArtistTrackViewState(spotifyApiResponse: SpotifyApiResponse) {
        val update = getCurrentViewState()
        if (update.spotifyApiResponse == spotifyApiResponse) {
            return
        }
        update.spotifyApiResponse = spotifyApiResponse
        _viewState.value = update
    }

    private fun getCurrentViewState(): MainViewState {
        return _viewState.value?.let { it } ?: MainViewState()
    }

    /**job cancellation*/
    fun cancelJob() {
        repository.cancelJob()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}