package com.example.timerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*

import kotlinx.coroutines.delay
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.timerapp.ui.theme.TimerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimerAppTheme {
                SimpleTimerApp()
            }
        }
    }

}



@Composable
fun SimpleTimerApp() {
    var timeLeft by remember { mutableStateOf(0) } // Initial time in seconds
    var inputTime by remember { mutableStateOf("") }
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        while (isRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        isRunning = false
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = inputTime,
            onValueChange = {
                inputTime = it
                timeLeft = it.toIntOrNull() ?: 0 // Convert input to Int safely
            },
            label = { Text("Enter Time in Seconds") },
            modifier = Modifier.padding(16.dp),
            textStyle = TextStyle(color = Color.Black)

        )

        Text(text = "Time Left: $timeLeft sec", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { isRunning = true }, enabled = !isRunning) {
                Text("Start")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { isRunning = false }, enabled = isRunning) {
                Text("Pause")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { timeLeft = 60; isRunning = false }) {
                Text("Reset")
            }
        }
    }
}


