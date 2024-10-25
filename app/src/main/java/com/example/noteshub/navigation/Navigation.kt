package com.example.noteshub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noteshub.screens.auth.AuthScreen
import com.example.noteshub.screens.auth.OtpVerificationUI
import com.example.noteshub.viewmodel.AuthViewModel
import com.example.noteshub.screens.home.HomeScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    // Use NavHost as a composable function
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        composable("auth") {
            AuthScreen(navController = navController, viewModel = authViewModel)
        }
        composable("otp_verification") {
            OtpVerificationUI(navController = navController, viewModel = authViewModel)
        }
        composable("home") {
            HomeScreen(navController = navController)  // Ensure HomeScreen is defined
        }
    }
}
