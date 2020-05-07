package com.victor.musicapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.victor.musicapp.data.database.entities.Track
import com.victor.musicapp.data.database.converter.AlbumConverter
import com.victor.musicapp.data.database.converter.AlbumImageConverter
import com.victor.musicapp.data.database.converter.ArtistsConverter
import com.victor.musicapp.data.database.converter.TrackItemConverter
import com.victor.musicapp.data.database.dao.SpotifyArtistTrackDao
import com.victor.musicapp.data.database.dao.TrackDao
import com.victor.musicapp.data.database.entities.SpotifyArtistTrackRequest

@Database(
    entities = [SpotifyArtistTrackRequest::class, Track::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    TrackItemConverter::class,
    AlbumConverter::class,
    ArtistsConverter::class,
    AlbumImageConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun spotifyArtistTrackDao(): SpotifyArtistTrackDao
    abstract fun trackDao(): TrackDao

}