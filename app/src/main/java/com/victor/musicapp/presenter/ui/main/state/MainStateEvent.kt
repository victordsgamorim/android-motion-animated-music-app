package com.victor.musicapp.presenter.ui.main.state

import com.victor.musicapp.domain.model.OAuthToken
import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest

sealed class MainStateEvent {

    data class SpotifyArtistTrackRequestEvent(
        var spotifyArtistTrackRequest: SpotifyArtistTrackRequest
    ) : MainStateEvent()

    data class OAuthTokenEvent(val oauthToken: OAuthToken) : MainStateEvent()
    data class SearchTokenDatabaseEvent(val id: Long) : MainStateEvent()
}