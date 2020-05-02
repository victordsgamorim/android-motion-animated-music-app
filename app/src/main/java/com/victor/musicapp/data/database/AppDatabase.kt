package com.victor.musicapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.victor.musicapp.data.database.dao.SpotifyArtistTrackDao
import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest

@Database(
    entities = [SpotifyArtistTrackRequest::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun spotifyArtistTrackDao(): SpotifyArtistTrackDao

}