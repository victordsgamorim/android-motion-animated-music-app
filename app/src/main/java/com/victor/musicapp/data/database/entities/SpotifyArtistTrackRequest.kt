package com.victor.musicapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "artist_track_token")
data class SpotifyArtistTrackRequest(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "auth_token")
    val authToken: String,

    @ColumnInfo(name = "auth_token_type")
    val authTokenType: String,

    @ColumnInfo(name = "accept")
    val acceptHeader: String = "application/json",

    @ColumnInfo(name = "content_type")
    val contentTypeHeader: String = "application/json",

    @ColumnInfo(name = "token_time")
    val tokenTime: Long = Calendar.getInstance().timeInMillis
)

