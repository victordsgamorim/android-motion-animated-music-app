package com.victor.musicapp.data.api.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest

@Entity(
    tableName = "track",
    foreignKeys = [ForeignKey(
        entity = SpotifyArtistTrackRequest::class,
        parentColumns = ["auth_token"],
        childColumns = ["tokenId"],
        onDelete = CASCADE,
        onUpdate = CASCADE
    )]
)
data class Track(

    @SerializedName("items")
    @Expose
    val items: List<TrackItem>,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "tokenId")
    @Expose
    val tokenId: String
)

data class TrackItem(

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("album")
    @Expose
    val album: Album,

    @SerializedName("artists")
    @Expose
    val artists: List<Artist>,

    @SerializedName("duration_ms")
    @Expose
    val duration_ms: Float,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("preview_url")
    @Expose
    val preview_url: String
)


class Album(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("album_type")
    @Expose
    val album_type: String,

    @SerializedName("artists")
    @Expose
    val artists: List<Artist>,

    @SerializedName("images")
    @Expose
    val images: List<AlbumImage>,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("release_date")
    @Expose
    val release_date: String,

    @SerializedName("type")
    @Expose
    val type: String

)

class Artist(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("name")
    @Expose
    val name: String
)

class AlbumImage(
    @SerializedName("width")
    @Expose
    val width: Int,

    @SerializedName("height")
    @Expose
    val height: Int,

    @SerializedName("url")
    @Expose
    val url: String
)





