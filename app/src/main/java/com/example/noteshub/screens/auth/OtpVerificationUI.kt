package com.example.noteshub.screens.auth


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.noteshub.viewmodel.AuthViewModel

@Composable
fun OtpVerificationUI(navController: NavController, viewModel: AuthViewModel) {
    var otpCode by remember { mutableStateOf("") }
    var isOtpValid by remember { mutableStateOf(true) }

    // Observe the authentication state
    val authState by viewModel.authState.collectAsState()

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
    ){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enter OTP sent to your phone",color = Color.White,
            fontSize = 26.sp)

        Spacer(modifier = Modifier.height(170.dp))

        OutlinedTextField(
            value = otpCode,
            colors = OutlinedTextFieldDefaults.colors(
                Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White,
            ),
            textStyle = TextStyle(fontSize = 18.sp),
            shape = RoundedCornerShape(12.dp),
            onValueChange = { otpCode = it },
            label = { Text("OTP") },
            isError = !isOtpValid,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (otpCode.length == 6) {
                    viewModel.verifyOtp(otpCode)
                } else {
                    isOtpValid = false // Handle invalid OTP case
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Verify OTP")
        }

        if (!isOtpValid) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Invalid OTP", color = Color.Red)
        }

        // Handle navigation based on auth state
        when (authState) {
            is AuthViewModel.AuthState.Success -> {
                navController.navigate("home")
            }
            is AuthViewModel.AuthState.Error -> {
                // Show error message
                Text(text = (authState as AuthViewModel.AuthState.Error).message, color = Color.Red)
            }
            else -> {}
        }
    }
    }
}
