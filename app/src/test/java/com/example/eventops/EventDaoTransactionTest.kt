package com.example.eventops

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import android.content.Context
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class EventDaoTransactionTest {

    private lateinit var database: EventDatabase
    private lateinit var eventDao: EventDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            EventDatabase::class.java
        ).allowMainThreadQueries().build()

        eventDao = database.eventDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun deleteTwoEvents_rollsBackWhenSecondEventDoesNotExist() =
        kotlinx.coroutines.runBlocking {

            // Arrange: Ein Event in die Testdatenbank einfügen
            val firstId = eventDao.insert(
                Event(
                    name = "Test Event",
                    startsAt = "2026-01-01",
                    location = "Berlin"
                )
            ).toInt()

            // Act: Das zweite Event existiert nicht
            try {
                eventDao.deleteTwoEvents(firstId, -1)
                fail("Expected IllegalStateException")
            } catch (e: IllegalStateException) {
                // Erwarteter Fehler
            }

            // Assert: Das erste Event muss wieder vorhanden sein
            assertNotNull(eventDao.getById(firstId))
        }
}