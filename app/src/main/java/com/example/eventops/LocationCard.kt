package com.example.eventops

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LocationCard(
    location: Location,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp)
        ) {
            Text(
                text = location.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text("ID: ${location.id}")
        }
    }
}