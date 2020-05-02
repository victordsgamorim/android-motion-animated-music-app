package com.victor.musicapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist_track_token")
data class SpotifyArtistTrackRequest(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "accept")
    val acceptHeader: String = "application/json",

    @ColumnInfo(name = "content_type")
    val contentTypeHeader: String = "application/json",

    @ColumnInfo(name = "auth_token_type")
    val authTokenType: String,

    @ColumnInfo(name = "auth_token")
    val authToken: String
)

