package com.portafolio.vientosdelsur.shared.dto

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val dayOff: Int,
    val hireDate: String,
    val occupation: String
)
