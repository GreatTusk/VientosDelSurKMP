package com.portafolio.vientosdelsur.shared.dto.shift

import com.portafolio.vientosdelsur.shared.dto.room.ShiftDateDto
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeScheduleDto(
    val workingDays: List<ShiftDateDto>,
    val daysOff: List<LocalDate>
)