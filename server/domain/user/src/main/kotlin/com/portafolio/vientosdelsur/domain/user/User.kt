package com.portafolio.vientosdelsur.domain.user

import kotlinx.datetime.LocalDateTime

data class User(
    val id: String,
    val email: String,
    val name: String,
    val photoUrl: String?,
    val phoneNumber: String,
    val isEnabled: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)