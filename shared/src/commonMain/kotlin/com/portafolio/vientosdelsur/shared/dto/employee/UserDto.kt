package com.portafolio.vientosdelsur.shared.dto.employee

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val userId: String,
    val photoUrl: String?,
    val isEnabled: Boolean,
    val email: String
)
