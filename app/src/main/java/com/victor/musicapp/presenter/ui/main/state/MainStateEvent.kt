package com.victor.musicapp.presenter.ui.main.state

import com.victor.musicapp.domain.model.OAuthToken
import com.victor.musicapp.data.database.entities.SpotifyArtistTrackRequest

sealed class MainStateEvent {

    object CheckTokenIntegrityEvent : MainStateEvent()

    data class OAuthTokenEvent(val oauthToken: OAuthToken) : MainStateEvent()

    object SearchTokenDatabaseEvent : MainStateEvent()

    data class SpotifyArtistTrackRequestEvent(
        var spotifyArtistTrackRequest: SpotifyArtistTrackRequest
    ) : MainStateEvent()

    data class ArtistDetailsEvent(val token: String, val id: String) : MainStateEvent()


}