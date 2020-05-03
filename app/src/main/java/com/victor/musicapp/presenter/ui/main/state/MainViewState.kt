package com.victor.musicapp.presenter.ui.main.state

import com.victor.musicapp.data.api.response.Track
import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest

class MainViewState(
    var spotifyArtistTrackRequest: SpotifyArtistTrackRequest? = null,
    var track: Track? = null,
    var event: MainStateEvent? = null
)
