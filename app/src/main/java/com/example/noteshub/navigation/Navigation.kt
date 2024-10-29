package com.example.noteshub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.noteshub.screens.auth.AuthScreen
import com.example.noteshub.screens.auth.OtpVerificationUI
import com.example.noteshub.screens.home.FolderDetailScreen
import com.example.noteshub.screens.home.HomeScreen
import com.example.noteshub.viewmodel.AuthViewModel
import com.example.noteshub.viewmodel.HomeViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("auth") {
            AuthScreen(
                navController = navController,
                viewModel = authViewModel,
                onLoginSuccess = {
                    // Define what happens on successful login
                    navController.navigate("otp_verification") {
                        popUpTo("auth") { inclusive = true } // Clear back stack
                    }
                }
            )
        }
        composable("otp_verification") {
            OtpVerificationUI(navController = navController, viewModel = authViewModel)
        }
        composable("home") {
            HomeScreen(
                navController = navController,
                viewModel = homeViewModel,
                authViewModel = authViewModel // Pass the AuthViewModel
            )
        }
        composable("folder/{folderName}") { backStackEntry ->
            val folderName = backStackEntry.arguments?.getString("folderName")
            val folder = homeViewModel.getFolderByName(folderName ?: "")
            if (folder != null) {
                FolderDetailScreen(navController = navController, folder = folder)
            } else {
                // Handle the case where the folder is not found, if needed
            }
        }
    }
}
