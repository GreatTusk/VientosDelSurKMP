package com.portafolio.vientosdelsur.feature.auth.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.onError
import com.portafolio.vientosdelsur.domain.auth.AuthError
import com.portafolio.vientosdelsur.domain.auth.oauth.GoogleAuthError
import com.portafolio.vientosdelsur.domain.auth.oauth.GoogleAuthService
import com.portafolio.vientosdelsur.domain.auth.signin.SignInUseCase
import com.portafolio.vientosdelsur.domain.auth.signup.SignUpUseCase
import kotlinx.coroutines.launch

internal class AuthViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val signInUseCase: SignInUseCase,
    private val googleAuthService: GoogleAuthService
) : ViewModel() {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set

    fun onEmailChanged(text: String) {
        email = text
    }

    fun onPasswordChanged(text: String) {
        password = text
    }

    fun onConfirmPasswordChanged(text: String) {
        confirmPassword = text
    }

    fun onSignIn() {
        viewModelScope.launch {
            signInUseCase(email, password)
                .onError {
                    when (it) {
                        AuthError.INVALID_EMAIL -> println("Invalid email address.")
                        AuthError.PASSWORD_MISMATCH -> println("Passwords do not match.")
                        AuthError.REMOTE -> println("Remote error occurred during sign up.")
                    }
                }
        }
    }

    fun onSignUp() {
        viewModelScope.launch {
            signUpUseCase(email, password, confirmPassword)
                .onError {
                    when (it) {
                        AuthError.INVALID_EMAIL -> println("Invalid email address.")
                        AuthError.PASSWORD_MISMATCH -> println("Passwords do not match.")
                        AuthError.REMOTE -> println("Remote error occurred during sign up.")
                    }
                }
        }
    }

    fun onSignInWithGoogle() {
        viewModelScope.launch {
            googleAuthService.login()
                .onError {
                    when (it) {
                        GoogleAuthError.NO_ACCOUNT_ON_DEVICE -> println("Your device has no google accounts")
                        GoogleAuthError.REMOTE -> println("Remote error occurred during sign in with google.")
                    }
                }
        }
    }
}