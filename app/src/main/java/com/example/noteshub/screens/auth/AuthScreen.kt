package com.example.noteshub.screens.auth

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteshub.viewmodel.AuthViewModel
import androidx.activity.ComponentActivity
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.example.noteshub.R

@Composable
fun AuthScreen(navController: NavController, viewModel: AuthViewModel) {
    var phoneNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") } // State for error message
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    // Observe the authentication state
    val authState by viewModel.authState.collectAsState()

    val offsetX = remember { Animatable(300f) } // Start off-screen to the right

    LaunchedEffect(Unit) {
        // Animate the text sliding in from the right
        offsetX.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1000) // Duration for the slide-in animation
        )
    }

    // Box with background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4A148C), // Purple color
                        Color.Black
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.padding(top = 76.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Notes Hub",
                color = Color.White,
                fontSize = 26.sp,
                modifier = Modifier
                    .offset(x = offsetX.value.dp) // Apply the animated offset
            )
            Spacer(modifier = Modifier.height(62.dp))

            Image(
                painter = painterResource(id = R.drawable.otp_verification),
                contentDescription = "Auth Screen",
                modifier = Modifier.size(192.dp)
            )

            Spacer(modifier = Modifier.height(102.dp))

            // Row to show +91 prefix and input for phone number
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "+91",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )

                // Input for phone number
                OutlinedTextField(
                    value = phoneNumber,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = {
                        // Allow only numbers and limit to 10 digits
                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                            phoneNumber = it
                        }
                    },
                    label = { Text("Phone Number", color = Color.LightGray) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.White,
                    ),
                    textStyle = TextStyle(fontSize = 18.sp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to send OTP
            Button(
                onClick = {
                    // Only send OTP if phone number has exactly 10 digits
                    if (phoneNumber.length == 10) {
                        activity?.let {
                            viewModel.sendOtp("+91$phoneNumber", it)
                            navController.navigate("otp_verification") // Navigate to OTP verification screen
                            errorMessage = "" // Clear any previous error messages
                        }
                    } else {
                        errorMessage = "Please enter a valid 10-digit phone number" // Set error message
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(start = 20.dp).fillMaxWidth(0.6f)
            ) {
                Text(text = "Submit", fontSize = 18.sp)
            }

            // Display error message if any
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = errorMessage, color = Color.Red)
            }

            // Handle navigation based on auth state (if needed)
            when (authState) {
                is AuthViewModel.AuthState.Error -> {
                    Text(text = (authState as AuthViewModel.AuthState.Error).message, color = Color.Red)
                }
                // Other states can be handled if necessary
                else -> {}
            }
        }
    }
}
