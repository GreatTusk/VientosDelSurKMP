package com.portafolio.vientosdelsur.domain.auth.register

import com.portafolio.vientosdelsur.domain.auth.Email

data class SignUpRequest(
    val email: Email,
    val password: String,
    val confirmPassword: String
)