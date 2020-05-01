package com.victor.musicapp.domain.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpotifyTokenResponse(
    @SerializedName("error")
    @Expose
    val error: String? = null,

    @SerializedName("error_description")
    @Expose
    val errorDescription: String? = null,

    @SerializedName("access_token")
    @Expose
    val accessToken: String? = null,

    @SerializedName("token_type")
    @Expose
    val tokenType: String? = null,

    @SerializedName("expires_in")
    @Expose
    val expiresIn: Int? = null,

    @SerializedName("scope")
    @Expose
    val scope: String? = null
) {

    override fun toString(): String {
        return "Token [error=$error - error_description=$errorDescription - " +
                "access_token=$accessToken - token_type=$tokenType - expires_in=$expiresIn " +
                "- scope=$scope]"
    }
}