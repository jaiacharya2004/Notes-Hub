package com.example.noteshub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteshub.screens.auth.AuthScreen
import com.example.noteshub.screens.home.HomeScreen
import com.example.noteshub.utils.PreferenceManager
import com.example.noteshub.viewmodel.AuthViewModel
import com.example.noteshub.viewmodel.HomeViewModel

class LoginActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PreferenceManager.init(this)
        authViewModel = AuthViewModel()
        homeViewModel = HomeViewModel()

        setContent {
            val navController = rememberNavController()
            AppNavigation(navController, authViewModel, homeViewModel)
        }
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = if (PreferenceManager.isLoggedIn()) "home" else "auth"
    ) {
        composable("auth") {
            AuthScreen(
                navController = navController,
                viewModel = authViewModel,
                onLoginSuccess = {
                    PreferenceManager.setLoggedIn(true)
                    navController.navigate("home") {
                        popUpTo("auth") { inclusive = true } // Clear the back stack
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(navController = navController, viewModel = homeViewModel, authViewModel = authViewModel)
        }
    }
}
