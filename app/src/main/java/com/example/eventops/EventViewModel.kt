package com.example.eventops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class EventViewModel(
    private val eventDao: EventDao
) : ViewModel() {

    fun observeEventsForLocation(
        locationId: Int
    ): StateFlow<List<Event>> {
        return eventDao.observeEventsForLocation(locationId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
    }
}