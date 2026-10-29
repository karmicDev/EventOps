package com.example.eventops

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Event::class, Location::class],
    version = 2,
    exportSchema = true
)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun locationDao(): LocationDao
}