package com.example.eventops

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Location",
    indices = [
        Index(value = ["name"], unique = true)
    ]
)
data class Location(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)
