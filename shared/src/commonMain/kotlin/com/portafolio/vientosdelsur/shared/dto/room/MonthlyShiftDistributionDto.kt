package com.portafolio.vientosdelsur.shared.dto.room

import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.shift.ScheduleDto
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class MonthlyShiftDistributionDto(
    val employee: EmployeeDto.Get,
    val scheduleDto: ScheduleDto
)

@Serializable
data class ShiftDto(
    val startTime: LocalTime,
    val endTime: LocalTime,
    val type: ShiftTypeDto
)

@Serializable
data class ShiftDateDto(
    val shift: ShiftDto,
    val date: LocalDate
)

@Serializable
enum class ShiftTypeDto {
    GENERAL_DUTY,
    KITCHEN_ASSISTANT,
    KITCHEN_LEAD
}