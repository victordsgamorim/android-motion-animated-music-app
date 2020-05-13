package com.victor.musicapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victor.musicapp.domain.dto.Artist

@Dao
interface ArtistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artists: List<Artist>?)

    @Query("SELECT * FROM Artist WHERE id = :id")
    suspend fun searchArtistById(id: String): Artist
}