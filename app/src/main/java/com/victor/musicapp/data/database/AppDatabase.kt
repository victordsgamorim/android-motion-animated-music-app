package com.victor.musicapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.victor.musicapp.data.database.converter.*
import com.victor.musicapp.data.database.entities.Track
import com.victor.musicapp.data.database.dao.ArtistDao
import com.victor.musicapp.data.database.dao.SpotifyArtistTrackDao
import com.victor.musicapp.data.database.dao.TrackDao
import com.victor.musicapp.data.database.entities.SpotifyArtistTrackRequest
import com.victor.musicapp.domain.dto.Artist

@Database(
    entities = [SpotifyArtistTrackRequest::class, Track::class, Artist::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    TrackItemConverter::class,
    AlbumConverter::class,
//    ArtistsConverter::class,
    AlbumImageConverter::class,
    ArtistExternalUrlConverter::class,
    ArtistFollowersConverter::class,
    ArtistImagesConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun spotifyArtistTrackDao(): SpotifyArtistTrackDao
    abstract fun trackDao(): TrackDao
    abstract fun artistDao(): ArtistDao

}