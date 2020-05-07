package com.victor.musicapp.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.victor.musicapp.domain.dto.artist.ArtistExternalUrl
import com.victor.musicapp.domain.dto.artist.ArtistFollowers
import com.victor.musicapp.domain.dto.artist.ArtistImages
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpotifyArtistResponse(


    @SerializedName("error")
    @Expose
    val error: SpotifyErrorApiResponse,

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("external_urls")
    @Expose
    val externalUrl: ArtistExternalUrl,

    @SerializedName("followers")
    @Expose
    val followers: ArtistFollowers,

    @SerializedName("images")
    @Expose
    val images: List<ArtistImages>,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("popularity")
    @Expose
    val popularity: Long

) : Parcelable
