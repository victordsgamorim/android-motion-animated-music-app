package com.victor.musicapp.data.database.dao

import androidx.room.*
import com.victor.musicapp.domain.model.SpotifyArtistTrackRequest

@Dao
interface SpotifyArtistTrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAuthToken(spotifyArtistTrack: SpotifyArtistTrackRequest): Long

    @Query("SELECT * FROM artist_track_token WHERE token_time = :tokenTime")
    suspend fun getToken(tokenTime: Long): SpotifyArtistTrackRequest

//    @Query("SELECT * FROM artist_track_token WHERE id = :id")
//    suspend fun getToken(id: Long): SpotifyArtistTrackRequest


}