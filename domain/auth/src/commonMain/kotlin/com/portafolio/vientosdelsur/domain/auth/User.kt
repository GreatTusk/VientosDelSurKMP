package com.portafolio.vientosdelsur.domain.auth

data class User(
    val name: String,
    val photoUrl: String?,
    val email: Email
)
