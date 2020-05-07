package com.victor.musicapp.domain.model

import java.util.*

data class OAuthToken(
    val oauthToken: String,
    val oauthTokenAccessMap: Map<String, String>,
    val tokenTime: Long = Calendar.getInstance().timeInMillis
) {

    override fun toString(): String {
        return "AuthToken [$oauthToken and $oauthTokenAccessMap]"
    }

}
