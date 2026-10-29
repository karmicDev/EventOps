package com.example.eventops

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EventCard(
    event: Event,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = event.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text("ID: ${event.id}")
            Text("Startdatum: ${event.startsAt}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventCardPreview() {
    EventCard(
        event = Event(id = 1, name = "Test", startsAt = "2023-01-01", locationId = 1001),
        modifier = Modifier.padding(16.dp)
    )
}
