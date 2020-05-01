package com.victor.musicapp.presenter.ui.main.state

import com.victor.musicapp.domain.dto.SpotifyApiResponse
import com.victor.musicapp.domain.dto.SpotifyTokenResponse

class MainViewState(
    var spotifyApiResponse: SpotifyApiResponse? = null,
    var spotifyTokenResponse: SpotifyTokenResponse? = null
)
