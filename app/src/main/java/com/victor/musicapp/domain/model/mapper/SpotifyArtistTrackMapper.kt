package com.victor.musicapp.domain.model.mapper

import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest

class SpotifyArtistTrackMapper(
    private val spotifyArtistTrack: SpotifyArtistTrackRequest
) {

    val tokenHeaderMap: Map<String, String> = mutableMapOf<String, String>()
        .apply {
            this["Accept"] = spotifyArtistTrack.acceptHeader
            this["Content-Type"] = spotifyArtistTrack.contentTypeHeader
            this["Authorization"] =
                "${spotifyArtistTrack.authTokenType} ${spotifyArtistTrack.authToken}"
        }

    val queryMapArtistMap = mutableMapOf<String, String>().apply {
        this["q"] = "Fresno"
        this["type"] = "track"
        this["limit"] = "10"
    }
}