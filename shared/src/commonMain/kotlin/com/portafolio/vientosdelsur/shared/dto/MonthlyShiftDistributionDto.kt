package com.portafolio.vientosdelsur.shared.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class MonthlyShiftDistributionDto(
    val employee: EmployeeDto,
    val sundaysOff: Set<LocalDate>,
    val shiftDate: List<ShiftDateDto>
)

data class ShiftDto(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val name: String
)

data class ShiftDateDto(
    val shift: ShiftDto,
    val date: LocalDate
)