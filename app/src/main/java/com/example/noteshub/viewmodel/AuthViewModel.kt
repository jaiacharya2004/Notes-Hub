package com.example.noteshub.viewmodel

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // To hold the verification ID after sending OTP
    var verificationId: String? = null  // Changed to public property for easier access

    // State to manage the UI
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    /**
     * Send OTP to the user's phone number.
     */
    fun sendOtp(phoneNumber: String, activity: ComponentActivity) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)              // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS)        // Timeout duration
                .setActivity(activity)                     // Pass the activity reference
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        // Auto-retrieval of the OTP is possible (instant verification)
                        signInWithPhoneAuthCredential(credential)
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        // Handle the error inside the onVerificationFailed method
                        _authState.value = AuthState.Error(e.message ?: "Verification failed")
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        this@AuthViewModel.verificationId = verificationId
                        _authState.value = AuthState.CodeSent
                    }
                })
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    /**
     * Verify OTP with Firebase.
     */
    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            verificationId?.let { id ->
                val credential = PhoneAuthProvider.getCredential(id, otp)
                signInWithPhoneAuthCredential(credential)
            } ?: run {
                _authState.value = AuthState.Error("Invalid verification ID")
            }
        }
    }

    /**
     * Sign in using phone credentials.
     */
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User signed in successfully
                    _authState.value = AuthState.Success
                } else {
                    // Sign-in failed
                    _authState.value = AuthState.Error(task.exception?.message ?: "Sign-in failed")
                }
            }
    }

    // Enum or sealed class to manage UI state
    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        object CodeSent : AuthState()  // OTP code sent
        object Success : AuthState()   // Auth successful
        data class Error(val message: String) : AuthState()
    }
}
