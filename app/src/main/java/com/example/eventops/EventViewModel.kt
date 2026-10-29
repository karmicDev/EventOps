package com.example.eventops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class EventViewModel(
    private val eventDao: EventDao
): ViewModel() {

    fun observeEventsForLocation(location: String): StateFlow<List<Event>> {
        return eventDao.observeEventsForLocation(location)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    val berlinEvents: StateFlow<List<Event>> =
        eventDao.observeEventsForLocation("Berlin")
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
}