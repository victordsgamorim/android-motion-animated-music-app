package com.victor.musicapp.presenter.ui.main.state

import com.victor.musicapp.domain.dto.SpotifyArtistTrackRequest

sealed class MainStateEvent {

    data class SpotifyArtistTrackRequestEvent(
        var spotifyArtistTrackRequest: SpotifyArtistTrackRequest
    ) : MainStateEvent()

}