package com.portafolio.vientosdelsur.domain.employee

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class Employee(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val dayOff: DayOfWeek,
    val hireDate: LocalDate,
    val occupation: String
)
