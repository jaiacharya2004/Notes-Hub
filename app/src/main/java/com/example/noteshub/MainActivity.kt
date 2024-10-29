package com.example.noteshub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.noteshub.navigation.SetupNavGraph
import com.example.noteshub.ui.theme.NotesHubTheme
import com.example.noteshub.utils.PreferenceManager
import com.example.noteshub.viewmodel.AuthViewModel
import com.example.noteshub.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check login status
        val isLoggedIn = PreferenceManager.isLoggedIn(this)

        setContent {
            // Initialize ViewModels within a composable context
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            val homeViewModel: HomeViewModel = viewModel()

            NotesHubTheme {
                SetupNavGraph(
                    navController = navController,
                    startDestination = if (isLoggedIn) "home" else "auth",  // Set start destination dynamically
                    authViewModel = authViewModel,
                    homeViewModel = homeViewModel
                )
            }
        }
    }
}
