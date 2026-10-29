package com.example.eventops

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

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

    @Query("DELETE FROM Event")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM Event WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Query("SELECT * FROM Event WHERE locationId = :locationId")
    suspend fun getEventsForLocation(locationId: Int): List<Event>

    @Query("SELECT COUNT(*) FROM Event WHERE locationId = :locationId")
    suspend fun countEventsForLocation(locationId: Int): Int

    @Query("SELECT * FROM Event WHERE locationId = :locationId")
    fun observeEventsForLocation(locationId: Int): Flow<List<Event>>

    @Transaction
    suspend fun deleteTwoEvents(id1: Int, id2: Int) {
        val deletedFirst = deleteById(id1)
        val deletedSecond = deleteById(id2)

        check(deletedFirst == 1 && deletedSecond == 1) {
            "Both events must exist and be deleted."
        }
    }

    @Transaction
    suspend fun getEventSummary(locationId: Int): EventSummary {
        val events = getEventsForLocation(locationId)
        val count = countEventsForLocation(locationId)

        return EventSummary(events, count)
    }
}