package com.goclean.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * Main entry point for Desktop (Windows/macOS/Linux) application
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "GoClean - Distraction Blocker"
    ) {
        MaterialTheme {
            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Using the shared Greeting class from KMP shared module
        val greeting = Greeting().greet()
        Text(text = greeting)
    }
}
