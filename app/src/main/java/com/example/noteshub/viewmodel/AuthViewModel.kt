package com.example.noteshub.viewmodel

import android.app.Activity
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class AuthViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    var verificationId: String? = null
    var phoneNumber: String? = null // Add this property to hold the phone number





    // State to manage the UI
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    /**
     * Sign in using phone number and verification code directly (for testing purposes).
     */

    fun signIn(phoneNumber: String, verificationCode: String, context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            // If verification code is provided, verify it
            if (verificationCode.isNotEmpty()) {
                val credential =
                    verificationId?.let { PhoneAuthProvider.getCredential(it, verificationCode) }
                if (credential != null) {
                    firebaseAuth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                _authState.value = AuthState.Success
                            } else {
                                val errorMessage = when (task.exception) {
                                    is FirebaseAuthInvalidCredentialsException -> "Invalid verification code"
                                    is FirebaseAuthInvalidUserException -> "User does not exist"
                                    else -> task.exception?.message ?: "Sign-in failed"
                                }
                                _authState.value = AuthState.Error(errorMessage)
                            }
                        }
                }
            } else {
                // Start the phone number verification process
                val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(context as? Activity ?: throw IllegalArgumentException("Context is not an Activity")) // Cast context to Activity
                    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                            // Automatically verify the code
                            firebaseAuth.signInWithCredential(credential)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        _authState.value = AuthState.Success
                                    } else {
                                        _authState.value = AuthState.Error(task.exception?.message ?: "Sign-in failed")
                                    }
                                }
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            _authState.value = AuthState.Error(e.message ?: "Verification failed")
                        }

                        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                            this@AuthViewModel.verificationId = verificationId
                            _authState.value = AuthState.OtpSent // Indicate that OTP was sent
                        }


                    })
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }
    }


    fun signOut() {
        firebaseAuth.signOut() // Sign out from Firebase
    }



    // Enum or sealed class to manage UI state
    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        object Success : AuthState() // Auth successful
        object OtpSent : AuthState() // OTP sent successfully
        data class Error(val message: String) : AuthState()
    }

}
