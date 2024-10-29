package com.example.noteshub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
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

        // Initialize PreferenceManager with context
        PreferenceManager.init(this)
        val isLoggedIn = PreferenceManager.isLoggedIn()

        setContent {
            NotesHubApp(isLoggedIn)
        }
    }
}


@Composable
fun NotesHubApp(isLoggedIn: Boolean) {
    // Initialize NavController and ViewModels
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()

    NotesHubTheme {
        SetupNavGraph(
            navController = navController,
            startDestination = if (isLoggedIn) "home" else "auth", // Dynamic start destination
            authViewModel = authViewModel,
            homeViewModel = homeViewModel
        )
    }
}
