package com.example.eventops

import androidx.room.Dao
import androidx.room.Query

@Dao
interface EventDao {
    @Query("SELECT * FROM Event")
    suspend fun getAll(): List<Event>

    @Query("SELECT * FROM Event WHERE id = :id")
    suspend fun getById(id: Int): Event?

    @Query("SELECT * FROM Event WHERE name = :name")
    suspend fun getByName(name: String): List<Event>

    @Query("SELECT * FROM Event ORDER BY name ASC")
    suspend fun getAllSortedByName(): List<Event>

    @Query("SELECT * FROM Event WHERE starts_at >:date ORDER BY starts_at")
    suspend fun getAfterDate(date: String): List<Event>
}