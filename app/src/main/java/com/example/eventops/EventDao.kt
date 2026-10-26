package com.example.eventops

import androidx.room.Dao
import androidx.room.Query

@Dao
interface EventDao {
    @Query("SELECT * FROM Event")
    suspend fun getAll(): List<Event>
}