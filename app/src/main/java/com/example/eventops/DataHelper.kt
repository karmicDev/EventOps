package com.example.eventops

import kotlin.random.Random

object DataHelper {
    fun createEvents(numEvents: Int = 10000): List<Event> {
        val events = mutableListOf<Event>()

        for (i in 1..numEvents) {
            events.add(
                Event(
                    name = getEventName(index = i),
                    startsAt = getEventDate(),
                    location = getEventLcation()
                )
            )
        }
        return events
    }

    private fun getEventName(index: Int): String {
        val year = Random.nextInt(2016, 2030)
        val season = getEventNameSeason()
        return String.format("#$index - $season Event - $year")
    }

    private fun getEventNameSeason() : String {
        return listOf("Winter", "Summer", "Autumn", "Spring").random()
    }

    private fun getEventDate(): String {
        val year = Random.nextInt(2016, 2030)
        val month = Random.nextInt(1, 13)
        val day = Random.nextInt(1, 29)
        return String.format("%04d-%02d-%02d", year, month, day)
    }

    private fun getEventLcation() : String {
        return listOf(
            "Berlin",
            "Hamburg",
            "München",
            "Köln",
            "Frankfurt",
            "Stuttgart",
            "Düsseldorf",
            "Dortmund",
            "Essen",
            "Leipzig").random()
    }
}