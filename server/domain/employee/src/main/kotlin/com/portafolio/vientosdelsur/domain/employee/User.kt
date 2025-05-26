package com.portafolio.vientosdelsur.domain.employee

import kotlinx.datetime.LocalDateTime

data class User(
    val id: String,
    val email: String,
    val photoUrl: String?,
    val phoneNumber: String,
    val isEnabled: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
