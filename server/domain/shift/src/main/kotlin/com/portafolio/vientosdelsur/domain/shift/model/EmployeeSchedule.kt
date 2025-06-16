package com.portafolio.vientosdelsur.domain.shift.model

import kotlinx.datetime.LocalDate

data class EmployeeSchedule(
    val workingDays: List<ShiftDate>,
    val daysOff: List<LocalDate>
)
