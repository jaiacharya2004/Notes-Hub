package com.example.noteshub.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

// If HomeScreen is missing, define it like this
@Composable
fun HomeScreen(navController: NavHostController) {
    // Simple UI for HomeScreen
    Text(text = "Welcome to the Home Screen")
}
