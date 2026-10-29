package com.example.eventops

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocationList(
    locations: List<Location>,
    onLocationClick: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = locations,
            key = { location -> if (location.id != 0) location.id else location.name }
        ) { location ->
            LocationCard(
                location = location,
                onClick = { onLocationClick(location) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}