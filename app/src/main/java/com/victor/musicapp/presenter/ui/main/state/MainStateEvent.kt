package com.victor.musicapp.presenter.ui.main.state

import com.victor.musicapp.domain.model.OAuthToken
import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest

sealed class MainStateEvent {

    object CheckTokenIntegrityEvent : MainStateEvent()

    data class OAuthTokenEvent(val oauthToken: OAuthToken) : MainStateEvent()

    object SearchTokenDatabaseEvent : MainStateEvent()

    data class SpotifyArtistTrackRequestEvent(
        var spotifyArtistTrackRequest: SpotifyArtistTrackRequest
    ) : MainStateEvent()


}