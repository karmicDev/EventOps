package com.example.eventops

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update

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

    @Query("SELECT * FROM Event WHERE starts_at > :date ORDER BY starts_at")
    suspend fun getAfterDate(date: String): List<Event>

    @Insert
    suspend fun insert(event: Event): Long

    @Insert
    suspend fun insert(events: List<Event>): List<Long>

    @Update
    suspend fun update(event: Event): Int

    @Update
    suspend fun update(events: List<Event>): Int

    @Delete
    suspend fun delete(event: Event): Int

    @Delete
    suspend fun delete(events: List<Event>): Int

    @Query("DELETE FROM Event WHERE id = :id")
    suspend fun deleteById(id: Int): Int
}