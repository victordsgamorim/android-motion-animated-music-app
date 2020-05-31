package com.victor.musicapp.presenter.ui.main

import androidx.lifecycle.LiveData
import com.victor.musicapp.data.database.entities.Track
import com.victor.musicapp.data.repository.MainRepository
import com.victor.musicapp.data.util.DataState
import com.victor.musicapp.domain.dto.Artist
import com.victor.musicapp.presenter.ui.BaseViewModel
import com.victor.musicapp.presenter.ui.main.state.MainStateEvent
import com.victor.musicapp.presenter.ui.main.state.MainViewState
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel<MainStateEvent, MainViewState>() {

    /**return the data triggered by the state event*/
    override fun handleStateEventResponse(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when (stateEvent) {
            is MainStateEvent.CheckTokenIntegrityEvent -> {
                repository.checkTokenIntegrity()
            }
            is MainStateEvent.SearchTokenDatabaseEvent -> {
                repository.getCurrentToken()
            }
            is MainStateEvent.OAuthTokenEvent -> {
                repository.generateNewToken(stateEvent.oauthToken)
            }
            is MainStateEvent.SpotifyArtistTrackRequestEvent -> {
                repository.getTrackResponse(stateEvent.spotifyArtistTrackRequest)
            }
            is MainStateEvent.InsertArtistsToDatabase -> {
                repository.insertArtistsDatabase(stateEvent.token, stateEvent.track)
            }
            is MainStateEvent.SearchForArtistDetails -> {
                repository.getArtist(stateEvent.id)
            }
        }
    }

    override fun initViewState(): MainViewState {
        return MainViewState()
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

    fun setArtistViewState(artist: Artist) {
        val update = getCurrentViewState()
        if (update.artist == artist) {
            return
        }
        update.artist = artist
        _viewState.value = update
    }

    override fun cancelJob() {
        repository.cancelJob()
    }
}