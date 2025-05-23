package com.portafolio.vientosdelsur.feature.auth.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.onError
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.auth.signup.SignUpError
import com.portafolio.vientosdelsur.domain.auth.signup.SignUpUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

internal class AuthViewModel(
    userRepository: UserRepository,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    val user = userRepository.currentUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(2.seconds),
        initialValue = null
    )

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

    fun onSignUp() {
        viewModelScope.launch {
            signUpUseCase.invoke(email, password, confirmPassword)
                .onError {
                    when (it) {
                        SignUpError.INVALID_EMAIL -> println("Invalid email address.")
                        SignUpError.PASSWORD_MISMATCH -> println("Passwords do not match.")
                        SignUpError.REMOTE -> println("Remote error occurred during sign up.")
                    }
                }
        }
    }
}