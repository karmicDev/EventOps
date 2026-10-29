package com.example.eventops

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class EventViewModel(
    private val eventDao: EventDao
) : ViewModel() {

    fun observeEventsForLocation(
        locationId: Int
    ): Flow<List<Event>> {
        return eventDao.observeEventsForLocation(locationId)
    }
}