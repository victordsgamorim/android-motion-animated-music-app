package com.victor.musicapp.domain.model

data class SpotifyArtistTrackRequest(
    val acceptHeader: String = "application/json",
    val contentTypeHeader: String = "application/json",
    val authTokenType: String,
    val authToken: String
) {

    val tokenHeaderMap: Map<String, String> = mutableMapOf<String, String>()
        .apply {
            this["Accept"] = acceptHeader
            this["Content-Type"] = contentTypeHeader
            this["Authorization"] = "$authTokenType $authToken"
        }

    val queryMapArtistMap = mutableMapOf<String, String>().apply {
        this["q"] = "Fresno"
        this["type"] = "track"
        this["limit"] = "10"
    }


}