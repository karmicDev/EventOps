package com.example.eventops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.eventops.ui.theme.EventOpsTheme
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = DatabaseProvider.getDatabase(this)
        val eventDao = database.eventDao()
        val locationDao = database.locationDao()

        val eventViewModelFactory = EventViewModelFactory(eventDao)
        val locationViewModelFactory = LocationViewModelFactory(locationDao)


       // insertTestData(database)

        val eventViewModel = ViewModelProvider(
            this,
            eventViewModelFactory
        )[EventViewModel::class.java]

        val locationViewModel = ViewModelProvider(
            this,
            locationViewModelFactory
        )[LocationViewModel::class.java]

        setContent {
            var selectedLocation by remember {
                mutableStateOf<Location?>(null)
            }
            EventOpsTheme {
                BackHandler(enabled = selectedLocation != null) {
                    selectedLocation = null
                }

                val locationsFlow = remember {
                    locationViewModel.locations
                }
                val locations by locationsFlow.collectAsStateWithLifecycle()

                val eventsFlow = remember(selectedLocation?.id) {
                    selectedLocation?.let { location ->
                        eventViewModel.observeEventsForLocation(location.id)
                    }
                }
                val events by (eventsFlow ?: flowOf(emptyList()))
                    .collectAsStateWithLifecycle(initialValue = emptyList())

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    if (selectedLocation == null) {
                        LocationList(
                            locations = locations,
                            onLocationClick = { location ->
                                selectedLocation = location
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        EventList(
                            events = events,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

private fun MainActivity.insertTestData(database: EventDatabase) {
    lifecycleScope.launch {
        val locationDao = database.locationDao()
        val eventDao = database.eventDao()

        val locations = DataHelper.getLocations()
        val locationIds = locationDao.insert(locations)

        val events = DataHelper.createEvents(
            numEvents = 10_000,
            locationIds = locationIds
        )
        eventDao.insert(events)
    }
}
