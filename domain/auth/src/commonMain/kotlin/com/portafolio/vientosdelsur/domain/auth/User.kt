package com.portafolio.vientosdelsur.domain.auth

data class User(
    val id: String,
    val name: String,
    val photoUrl: String?,
    val email: Email,
    val isActive: Boolean
)
