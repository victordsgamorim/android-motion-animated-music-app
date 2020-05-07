package com.victor.musicapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victor.musicapp.data.database.entities.Track

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrack(track: Track): Long

    @Query("SELECT * FROM track WHERE tokenId = :token")
    fun getTrackByTheToken(token: String): Track

}