package com.portafolio.vientosdelsur.feature.auth.screens

import androidx.lifecycle.ViewModel
import com.portafolio.vientosdelsur.domain.auth.login.SignInRepository
import com.portafolio.vientosdelsur.domain.auth.register.SignUpRepository

internal class AuthViewModel(
    private val signUpRepository: SignUpRepository,
    private val signInRepository: SignInRepository
): ViewModel() {
}