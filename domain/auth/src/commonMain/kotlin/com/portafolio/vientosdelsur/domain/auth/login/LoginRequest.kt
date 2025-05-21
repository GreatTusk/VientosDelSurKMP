package com.portafolio.vientosdelsur.domain.auth.login

import com.portafolio.vientosdelsur.domain.auth.Email

data class LoginRequest(
    val email: Email,
    val password: String
)