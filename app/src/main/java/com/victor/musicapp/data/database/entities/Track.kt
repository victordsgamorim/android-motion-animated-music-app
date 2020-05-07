package com.victor.musicapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.victor.musicapp.domain.dto.TrackItem
import com.victor.musicapp.data.database.entities.SpotifyArtistTrackRequest

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







