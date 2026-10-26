package com.example.eventops

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: EventDatabase? = null

    fun getDatabase(context: Context): EventDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                EventDatabase::class.java,
                "event_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}