package com.example.noteshub.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.noteshub.utils.PreferenceManager
import com.example.noteshub.viewmodel.AuthViewModel

@Composable
fun OtpVerificationUI(navController: NavController, viewModel: AuthViewModel) {
    var verificationCode by remember { mutableStateOf("") }
    var isCodeValid by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4A148C))
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
            Spacer(modifier = Modifier.height(110.dp))

            Image(painter = painterResource(id =R.drawable.otp_verification ), contentDescription = "Otp Verification Image")

            Spacer(modifier = Modifier.height(110.dp))

            OutlinedTextField(
                value = verificationCode,
                onValueChange = { code ->
                    verificationCode = code
                    isCodeValid = code.length <= 6 && code.all { it.isDigit() }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color.Black
                ),
                textStyle = TextStyle(fontSize = 24.sp, color = Color.Black),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (verificationCode == "123456") {
                        // Navigate to home screen
                        PreferenceManager.setLoggedIn(navController.context, true)
                        navController.navigate("home") {
                            popUpTo("otp_verification") { inclusive = true }
                        }
                    } else {
                        isCodeValid = false // Set invalid flag if code doesn't match
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(text = "Verify Code")
            }

            if (!isCodeValid) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Invalid Code", color = Color.Red)
            }
        }
    }
}
