package com.portafolio.vientosdelsur.shared.dto

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val dayOff: DayOfWeek,
    val hireDate: LocalDate,
    val occupation: String,
    val role: RoleDataDto? = null
)

@Serializable
data class RoleDataDto(
    val role: String
)
