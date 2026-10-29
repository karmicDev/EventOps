package com.example.eventops

import kotlin.random.Random

object DataHelper {

    fun getLocations(): List<Location> {
        return listOf(
            Location(name = "Berlin"),
            Location(name = "Hamburg"),
            Location(name = "München"),
            Location(name = "Köln"),
            Location(name = "Frankfurt"),
            Location(name = "Stuttgart"),
            Location(name = "Düsseldorf"),
            Location(name = "Dortmund"),
            Location(name = "Essen"),
            Location(name = "Leipzig")
        )
    }
    fun createEvents(numEvents: Int = 10000, locationIds: List<Long>): List<Event> {
        require(locationIds.isNotEmpty()) {
            "At least one location ID is required."
        }

        val events = mutableListOf<Event>()

        for (i in 1..numEvents) {
            events.add(
                Event(
                    name = getEventName(index = i),
                    startsAt = getEventDate(),
                    locationId = locationIds.random().toInt()
                )
            )
        }
        return events
    }
    private fun getEventDate(): String {
        val year = Random.nextInt(2006, 2030)
        val month = Random.nextInt(1, 13)
        val day = Random.nextInt(1, 29)
        return String.format("%04d-%02d-%02d", year, month, day)
    }

    private fun getEventName(index: Int): String {
        val titles = listOf(
            "Sounds of Summer",
            "Future of AI",
            "Urban Gardening",
            "Code & Coffee",
            "Digital Art",
            "Indie Film",
            "Green City",
            "Startup Founders",
            "Electronic Nights",
            "Food & Culture",
            "Design Futures",
            "Science Unplugged",
            "Mindful Living",
            "Creative Minds",
            "Open Source Days",
            "Beyond the Screen",
            "Future in Motion",
            "The Creative Lab",
            "Voices of Tomorrow",
            "The Innovation Project",
            "Hidden Worlds",
            "New Perspectives",
            "Art After Dark",
            "The Science of Sound",
            "Sustainable Futures",
            "Digital Horizons",
            "The Human Experience",
            "Next Generation",
            "Curious Minds",
            "The Great Experiment",
            "Into the Unknown",
            "Beyond Boundaries",
            "The Culture Club",
            "Bright Ideas",
            "Future Makers",
            "The Green Revolution",
            "Sound & Vision",
            "The Learning Lab",
            "Creative Connections",
            "The Discovery Series",
            "Wild Ideas",
            "Modern Rituals",
            "The Big Picture",
            "Signals & Stories",
            "New Frequencies",
            "The Art of Change",
            "Tomorrow Today",
            "Open Minds",
            "The Next Chapter",
            "Human After All"
        )

        val eventTypes = listOf(
            "Festival",
            "Conference",
            "Workshop",
            "Meetup",
            "Exhibition",
            "Film Night",
            "Summit",
            "Market",
            "Live Session",
            "Community Day",
            "Symposium",
            "Masterclass",
            "Showcase",
            "Panel Discussion",
            "Hackathon",
            "Lecture",
            "Roundtable",
            "Performance",
            "Listening Session",
            "Creative Retreat",
            "Pop-up Experience",
            "Networking Night",
            "Open Studio",
            "Talk Series",
            "Challenge",
            "Convention",
            "Demo Day",
            "Book Club",
            "Tasting Experience",
            "Research Forum",
            "Celebration",
            "Workshop Series",
            "Interactive Experience",
            "Night Market",
            "Science Fair",
            "Art Walk",
            "Innovation Lab",
            "Public Talk",
            "Design Sprint",
            "Community Meetup"
        )

        val editions = listOf(
            "Open Air",
            "Night Session",
            "Spring Edition",
            "Summer Edition",
            "Autumn Edition",
            "Winter Edition",
            "Special Edition",
            "Annual Edition",
            "After Hours",
            "The Next Chapter",
            "Limited Edition",
            "Extended Version",
            "Unplugged",
            "After Dark",
            "The Grand Finale",
            "The Opening Session",
            "Weekend Edition",
            "Late Night Edition",
            "The Experience",
            "Behind the Scenes",
            "The Reunion",
            "The Next Wave",
            "Anniversary Edition",
            "The Remix",
            "The Collective",
            "The Final Chapter",
            "Beyond the Basics",
            "The Deep Dive",
            "The Essentials",
            "The Experiment"
        )

        val title = titles.random()
        val eventType = eventTypes.random()

        return when (index % 3) {
            0 -> "#$index - $title $eventType"
            1 -> "#$index - $title - ${editions.random()}"
            else -> "#$index - $title $eventType ${Random.nextInt(2006, 2030)}"
        }
    }

}