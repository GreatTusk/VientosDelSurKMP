package com.portafolio.vientosdelsur.domain.auth.signin

import com.portafolio.vientosdelsur.domain.auth.Email

data class SignInRequest(
    val email: Email,
    val password: String
)