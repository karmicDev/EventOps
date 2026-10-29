package com.example.eventops

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.eventops.ui.theme.EventOpsTheme
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


        insertTestData(database)

        val eventViewModel = ViewModelProvider(
            this,
            eventViewModelFactory
        )[EventViewModel::class.java]

        val locationViewModel = ViewModelProvider(
            this,
            locationViewModelFactory
        )[LocationViewModel::class.java]

        setContent {
            EventOpsTheme {
                val locationsFlow = remember {
                    locationViewModel.locations
                }
                val locations by locationsFlow.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    LocationList(
                        locations = locations,
                        onLocationClick = { location ->
                            println("Selected location: ${location.name}, ID: ${location.id}")
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
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
