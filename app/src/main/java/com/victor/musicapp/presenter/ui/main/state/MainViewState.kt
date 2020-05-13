package com.victor.musicapp.presenter.ui.main.state

import com.victor.musicapp.data.database.entities.SpotifyArtistTrackRequest
import com.victor.musicapp.data.database.entities.Track
import com.victor.musicapp.domain.dto.Artist

class MainViewState(
    var spotifyArtistTrackRequest: SpotifyArtistTrackRequest? = null,
    var track: Track? = null,
    var event: MainStateEvent? = null,
    var token: String? = null,
    var artist: Artist? = null
)
