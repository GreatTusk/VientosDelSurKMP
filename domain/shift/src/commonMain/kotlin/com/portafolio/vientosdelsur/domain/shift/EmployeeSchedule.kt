package com.portafolio.vientosdelsur.domain.shift

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class EmployeeSchedule(
    val workingDays: List<ShiftDate>,
    val daysOff: List<LocalDate>
)

data class ShiftDate(
    val shift: Shift,
    val date: LocalDate
)

data class Shift(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val type: String
)