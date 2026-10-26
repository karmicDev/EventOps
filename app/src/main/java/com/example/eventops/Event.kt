package com.example.eventops

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "Event",
    indices = [Index(value = ["name"], unique = true)]
)
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,

    @ColumnInfo(name = "starts_at")
    val startsAt: String,

    val location: String = "Unknown",
)