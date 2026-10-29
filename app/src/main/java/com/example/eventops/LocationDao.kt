package com.example.eventops

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert
    suspend fun insert(locations: List<Location>): List<Long>

    @Query("SELECT * FROM Location ORDER BY name ASC")
    suspend fun getAll(): List<Location>

    @Query("SELECT * FROM Location ORDER BY name ASC")
    fun observeAll(): Flow<List<Location>>
}