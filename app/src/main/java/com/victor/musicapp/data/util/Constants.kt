package com.victor.musicapp.data.util

const val ONE_HOUR_IN_MILLIS = 3600000
const val DATABASE_NAME = "musicapp.db"

object SpotifyConstants {

    //artist track
    const val BASE_URL_ALBUM = "https://api.spotify.com/v1/"

    //generate token
    const val BASE_URL_TOKEN = "https://accounts.spotify.com/api/"

    val OAUTH_TOKEN_HEADER =
        "Basic NThiNzRmNDhmNjY0NDY1OTgyMGRlNDcwY2UyZDVjMjk6ODJlOTYwYjM1ODI3NDQ4NTg3OTA2ZjgyNTc4MDJiNTA="

    val OAUTH_TOKEN_ACCESS_MAP = mutableMapOf<String, String>().apply {
        this["grant_type"] = "refresh_token"
        this["refresh_token"] =
            "AQBSOGxyBKm1edNYEIneyo2Xzl_-KazYogl-fET633xSCS02-zsD0eGUtWSBeDDYnBZ4dKYC3xn4HO2d1DEUNEXk1fbTp7vECvcXiavFhwWTuzSpA9Q1km9h0SQIaUJ-cz0"
    }
}

object SharedPreferencesConstants {

    const val SHARED_PREFERENCES_NAME = "com.victor.musicapp.Token"
    const val GENERATED_TOKEN_TIME = "GeneratedTokenTime"
    const val GENERATED_TOKEN_ID = "GeneratedToken_ID"
    const val LONG_DEFAULT = -1L

}