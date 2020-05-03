package com.victor.musicapp.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "artist_track_token")
data class SpotifyArtistTrackRequest(

    /**doing that way it will only return 1 as ID
     * the reason for doing that is because I dont want to store more than a token every time
     * I generate a new one, but replacing the existed one and replacing it.*/
//    @PrimaryKey(autoGenerate = false)
//    val id: Long = 1L,

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

