package com.victor.musicapp.data.util

object SpotifyConstants {

    const val BASE_URL = "https://api.spotify.com/v1/"
    private const val HEADER_ACCEPT = "application/json"
    private const val HEADER_CONTENT_TYPE = "application/json"
    private const val AUTH_TOKEN =
        "BQBsJI0_ovB6CfEpUEwH9Z3UViK35aEGfCADnzu91ywJwB4tTW-yHCNTCFPBn_1x1_dYMTM151GzaBKEWZtMZAiwnKuin9q-g-ar6jJJciYkEhlP3VgLEWFJ6cLV8GwAX2WXK5Y9279dTWq4RvHctpFDdvyaBI2b8Pgj5P7KijTA6Ybuu9-uztu98iWysbM3-IKYTZLlLHmLnYALDUyCyKrZ4s22XyNtMsKc6TB0VkUwydudV9a6oUH8CZdch5vg82d4bSmfpFvjbhbqnQ"

    val AUTH_HEADER_MAP = mutableMapOf<String, String>().apply {
        this["Accept"] = HEADER_ACCEPT
        this["Content-Type"] = HEADER_CONTENT_TYPE
        this["Authorization"] = "Bearer $AUTH_TOKEN"
    }

    const val SPOTIFY_ARTIST = "Fresno"
    const val SPOTIFY_TYPE = "track"
    const val ALBUM_LIMIT = 10


}