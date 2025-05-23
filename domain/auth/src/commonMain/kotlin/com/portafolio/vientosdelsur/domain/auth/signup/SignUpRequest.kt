package com.portafolio.vientosdelsur.domain.auth.signup

import com.portafolio.vientosdelsur.domain.auth.Email

data class SignUpRequest(
    val email: Email,
    val password: String
)