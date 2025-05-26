package com.portafolio.vientosdelsur.shared.dto.room

import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class MonthlyShiftDistributionDto(
    val employee: EmployeeDto,
    val sundaysOff: Set<LocalDate>,
    val shiftDate: List<ShiftDateDto>
)

@Serializable
data class ShiftDto(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val name: String
)

@Serializable
data class ShiftDateDto(
    val shift: ShiftDto,
    val date: LocalDate
)