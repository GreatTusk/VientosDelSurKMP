package com.portafolio.vientosdelsur.domain.shift

import com.portafolio.vientosdelsur.domain.employee.Employee
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class EmployeeSchedule(
    val employee: Employee,
    val schedule: Schedule
)

data class Schedule(
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
    val type: ShiftType
)

enum class ShiftType {
    GENERAL_DUTY,
    KITCHEN_ASSISTANT,
    KITCHEN_LEAD
}
