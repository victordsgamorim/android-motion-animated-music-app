package com.victor.musicapp.presenter.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.victor.musicapp.data.repository.MainRepository
import com.victor.musicapp.domain.dto.SpotifyApiResponse
import com.victor.musicapp.domain.dto.SpotifyTokenResponse
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent.SpotifyArtistTrackRequestEvent
import com.victor.musicapp.presenter.ui.main.state.MainViewState
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _stateEvent = MutableLiveData<MainStateEvent>()
    private val _viewState = MutableLiveData<MainViewState>()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<MainViewState> =
        switchMap(_stateEvent) { stateEvent ->
            when (stateEvent) {
                is SpotifyArtistTrackRequestEvent -> {
                    repository.getTrackResponse(stateEvent.spotifyArtistTrackRequest)
                }
            }
        }

    fun addEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }


    fun getNewToken(): LiveData<SpotifyTokenResponse> {
        return repository.getNewToken()
    }

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
}