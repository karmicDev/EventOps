package com.example.eventops

import androidx.room.Query

interface EventDao {
    @Query("SELECT * FROM Event")
    suspend fun getAll(): List<Event>
}