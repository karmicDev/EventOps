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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.eventops.ui.theme.EventOpsTheme
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = DatabaseProvider.getDatabase(this)
        val eventDao = database.eventDao()
        lifecycleScope.launch {
            //val deleteCount = eventDao.deleteAll()
            //println("Deleted Events count: $deleteCount")
           // eventDao.deleteAll()
           //eventDao.insert(DataHelper.createEvents())

            val berlinFlow = eventDao.observeEventsForLocation("Berlin")
            val hamburgFlow = eventDao.observeEventsForLocation("Hamburg")

            combine(berlinFlow, hamburgFlow) { berlin, hamburg ->
                CityEvents(berlin, hamburg)
            }.collect { result ->
                println("Berlin: ${result.berlin.size}")
                println("Hamburg: ${result.hamburg.size}")
            }

        }
        setContent {
            EventOpsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EventOpsTheme {
        Greeting("Android")
    }
}