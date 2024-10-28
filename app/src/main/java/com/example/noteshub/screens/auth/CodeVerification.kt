package com.example.noteshub.screens.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.noteshub.R
import com.example.noteshub.viewmodel.AuthViewModel

@Composable
fun OtpVerificationUI(navController: NavController, viewModel: AuthViewModel) {
    val otpCodeList = remember { mutableStateListOf("", "", "", "", "", "") }
    var isOtpValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") } // Add a state for error message

    // Observe the authentication state
    val authState by viewModel.authState.collectAsState()

    // Create focus managers for each OTP field
    val focusRequesterList = List(6) { FocusRequester() }

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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 92.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter Verification Code",
                color = Color.White,
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(130.dp))

            Image(
                painter = painterResource(id = R.drawable.otp_verification),
                contentDescription = "Auth Screen",
                modifier = Modifier.size(192.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 36.dp)
            ) {
                for (i in 0 until 6) {
                    OutlinedTextField(
                        value = otpCodeList[i],
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = Color.Black
                        ),
                        textStyle = TextStyle(fontSize = 24.sp, color = Color.Black),
                        shape = RoundedCornerShape(12.dp),
                        onValueChange = { newValue ->
                            if (newValue.isEmpty()) {
                                otpCodeList[i] = "" // Clear current field
                                if (i > 0) {
                                    focusRequesterList[i - 1].requestFocus() // Move focus to previous field
                                }
                            } else if (newValue.length == 1) {
                                otpCodeList[i] = newValue // Update field
                                if (i < 5) {
                                    focusRequesterList[i + 1].requestFocus() // Move to next field if not the last one
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                        modifier = Modifier
                            .width(45.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .focusRequester(focusRequesterList[i])
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val otpCode = otpCodeList.joinToString("") // Combine the OTP digits
                    if (otpCode.length == 6) {
                        viewModel.verifyOtp(otpCode)
                        errorMessage = "" // Clear any previous error message
                    } else {
                        isOtpValid = false // Handle invalid OTP case
                        errorMessage = "Please enter the verification code" // Set error message
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(text = "Verify Code")
            }

            // Display error message if OTP is invalid or empty
            if (!isOtpValid || errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = errorMessage, color = Color.Red)
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
