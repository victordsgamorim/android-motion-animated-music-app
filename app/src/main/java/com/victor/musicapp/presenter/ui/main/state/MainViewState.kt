package com.victor.musicapp.presenter.ui.main.state

import com.victor.musicapp.data.api.response.SpotifyArtistResponse
import com.victor.musicapp.data.database.entities.SpotifyArtistTrackRequest
import com.victor.musicapp.data.database.entities.Track

class MainViewState(
    var spotifyArtistTrackRequest: SpotifyArtistTrackRequest? = null,
    var track: Track? = null,
    var event: MainStateEvent? = null,
    var spotifyArtistResponse: SpotifyArtistResponse? = null
)
