package com.victor.musicapp.domain.model

data class OAuthToken(val oauthToken: String, val oauthTokenAccessMap: Map<String, String>) {


    override fun toString(): String {
        return "AuthToken [$oauthToken and $oauthTokenAccessMap]"
    }

}
