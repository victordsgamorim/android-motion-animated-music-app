package com.victor.musicapp.presenter.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.victor.musicapp.data.database.entities.Track
import com.victor.musicapp.data.repository.MainRepository
import com.victor.musicapp.data.util.DataState
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
                is CheckTokenIntegrityEvent -> {
                    repository.checkTokenIntegrity()
                }
                is SearchTokenDatabaseEvent -> {
                    repository.getCurrentToken()
                }
                is OAuthTokenEvent -> {
                    repository.generateNewToken(stateEvent.oauthToken)
                }
                is SpotifyArtistTrackRequestEvent -> {
                    repository.getTrackResponse(stateEvent.spotifyArtistTrackRequest)
                }
                is InsertArtistsToDatabase -> {
                    repository.getArtists(stateEvent.token, stateEvent.track)
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
    fun setTrackViewState(track: Track) {
        val update = getCurrentViewState()
        if (update.track == track) {
            return
        }
        update.track = track
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