package com.example.noteshub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.noteshub.screens.auth.OtpVerificationUI
import com.example.noteshub.viewmodel.AuthViewModel

class OtpVerificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Call your OtpVerificationUI here
            OtpVerificationUI(navController = rememberNavController(), viewModel = AuthViewModel())
        }
    }
}
