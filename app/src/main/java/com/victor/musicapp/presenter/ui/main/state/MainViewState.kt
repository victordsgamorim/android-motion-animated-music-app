package com.victor.musicapp.presenter.ui.main.state

import com.victor.musicapp.data.api.response.SpotifyApiResponse
import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest

class MainViewState(
    var spotifyApiResponse: SpotifyApiResponse? = null,
    val spotifyArtistTrackRequest: SpotifyArtistTrackRequest? = null
)
