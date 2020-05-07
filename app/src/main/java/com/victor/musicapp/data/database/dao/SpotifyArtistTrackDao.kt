package com.victor.musicapp.data.database.dao

import androidx.room.*
import com.victor.musicapp.data.database.entities.SpotifyArtistTrackRequest

@Dao
interface SpotifyArtistTrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAuthToken(spotifyArtistTrack: SpotifyArtistTrackRequest): Long

    @Query("SELECT * FROM artist_track_token WHERE token_time = :tokenTime")
    suspend fun getToken(tokenTime: Long): SpotifyArtistTrackRequest

    @Query("SELECT COUNT(auth_token) FROM artist_track_token")
    suspend fun getRowCount(): Int

    @Query("DELETE FROM artist_track_token")
    suspend fun cleanTable()


}