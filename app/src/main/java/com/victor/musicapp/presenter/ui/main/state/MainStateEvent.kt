package com.victor.musicapp.presenter.ui.main.state

import com.victor.musicapp.domain.model.OAuthToken
import com.victor.musicapp.data.database.entities.SpotifyArtistTrackRequest
import com.victor.musicapp.data.database.entities.Track

sealed class MainStateEvent {

    object CheckTokenIntegrityEvent : MainStateEvent()

    data class OAuthTokenEvent(val oauthToken: OAuthToken) : MainStateEvent()

    object SearchTokenDatabaseEvent : MainStateEvent()

    data class SpotifyArtistTrackRequestEvent(
        var spotifyArtistTrackRequest: SpotifyArtistTrackRequest
    ) : MainStateEvent()

    data class InsertArtistsToDatabase(val token: String, val track: Track) : MainStateEvent()
    data class SearchForArtistDetails(val id: String) : MainStateEvent()


}