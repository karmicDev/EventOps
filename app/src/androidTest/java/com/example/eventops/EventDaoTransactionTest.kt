package com.example.eventops

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class EventDaoTransactionTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.eventops", appContext.packageName)
    }

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

    @Test
    fun observeEventsForLocation_returnsMatchingEvents() = runTest {
        // Arrange:
        eventDao.insert(
            Event(
                name = "Berlin Meetup",
                startsAt = "2026-11-15",
                location = "Berlin"
            )
        )
        eventDao.insert(
            Event(
                name = "Hamburg Meetup",
                startsAt = "2026-11-20",
                location = "Hamburg"
            )
        )

        // Act
        val events = eventDao.observeEventsForLocation("Berlin").first()

        // Assert
        assertEquals(1, events.size)
        assertEquals("Berlin Meetup", events[0].name)

    }

    @Test
    fun observeEventsForLocation_emitsUpdatedListAfterInsert() = runTest {
        eventDao.insert(
            Event(
                name = "Berlin Meetup",
                startsAt = "2026-11-15",
                location = "Berlin"
            )
        )

        eventDao.observeEventsForLocation("Berlin").test {
            // 1. Ersten Datenbankstand prüfen
            val initialEvents = awaitItem()
            assertEquals(1, initialEvents.size)

            // 2. Zweites Event einfügen
            eventDao.insert(
                Event(
                    name = "Berlin Conference",
                    startsAt = "2026-11-20",
                    location = "Berlin"
                )
            )

            // 3. Aktualisierten Datenbankstand prüfen
            val updatedEvents = awaitItem()
            assertEquals(2, updatedEvents.size)

            cancelAndIgnoreRemainingEvents()
        }
    }
}