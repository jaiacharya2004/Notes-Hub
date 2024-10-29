package com.example.noteshub.screens.auth

import androidx.compose.animation.core.Animatable
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteshub.viewmodel.AuthViewModel
import com.example.noteshub.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.launch
import com.example.noteshub.viewmodel.TestPhoneNumbers

@Composable
fun AuthScreen(navController: NavController, viewModel: AuthViewModel, onLoginSuccess: () -> Unit) {
    var phoneNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()


    val offsetX = remember { Animatable(300f) } // Start off-screen to the right

    LaunchedEffect(Unit) {
        // Animate the text sliding in from the right
        offsetX.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1000) // Duration for the slide-in animation
        )
    }



    // Handle login success
    LaunchedEffect(authState) {
        when (authState) {
            is AuthViewModel.AuthState.Success -> {
                onLoginSuccess() // This should navigate to "home" or handle successful login
            }
            is AuthViewModel.AuthState.Error -> {
                errorMessage = (authState as AuthViewModel.AuthState.Error).message
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4A148C))
    ) {
        Column(
            modifier = Modifier.padding(top = 76.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Notes Hub",
                color = Color.White,
                fontSize = 26.sp,
                modifier = Modifier.offset(x = offsetX.value.dp)
            )
            Spacer(modifier = Modifier.height(62.dp))

            Image(
                painter = painterResource(id = R.drawable.otp_verification),
                contentDescription = "Auth Screen",
                modifier = Modifier.size(192.dp)
            )

            Spacer(modifier = Modifier.height(102.dp))

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

                OutlinedTextField(
                    value = phoneNumber,
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = {
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
                        cursorColor = Color.White
                    ),
                    textStyle = TextStyle(fontSize = 18.sp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val fullPhoneNumber = "+91$phoneNumber"
                    if (phoneNumber.length == 10) {
                        if (TestPhoneNumbers.validNumbers.contains(fullPhoneNumber)) {
                            errorMessage = "" // Clear previous error messages
                            viewModel.phoneNumber = fullPhoneNumber // Set the phone number in the ViewModel
                            navController.navigate("otp_verification") // Navigate to OTP verification UI
                        } else {
                            errorMessage = "Invalid phone number"
                        }
                    } else {
                        errorMessage = "Please enter a valid 10-digit phone number"
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(start = 20.dp).fillMaxWidth(0.6f)
            ) {
                Text(text = "Submit", fontSize = 18.sp)
            }

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = errorMessage, color = Color.Red)
            }
        }
    }
}
