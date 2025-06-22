package com.portafolio.vientosdelsur.service.shift.mapper

import com.portafolio.vientosdelsur.domain.shift.model.EmployeeSchedule
import com.portafolio.vientosdelsur.shared.dto.shift.ScheduleDto

fun EmployeeSchedule.toEmployeeScheduleDto() =
    ScheduleDto(
        workingDays = workingDays.map { it.toShiftDateDto() },
        daysOff = daysOff
    )
