package com.example.noteshub

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.noteshub.utils.PreferenceManager
import com.example.noteshub.screens.auth.AuthScreen
import com.example.noteshub.viewmodel.AuthViewModel

class LoginActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel // Declare ViewModel without Koin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the ViewModel using ViewModelProvider
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        setContent {
            val navController = rememberNavController() // Create a NavController

            AuthScreen(
                navController = navController,
                viewModel = authViewModel,
                onLoginSuccess = {
                    // Save login status
                    PreferenceManager.setLoggedIn(this, true)
                    // Navigate to OtpVerificationActivity after successful login
                    startActivity(Intent(this, OtpVerificationActivity::class.java))
                    finish()
                }
            )

        }
    }
}
