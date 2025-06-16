package com.portafolio.vientosdelsur.service.shift.mapper

import com.portafolio.vientosdelsur.domain.shift.model.EmployeeSchedule
import com.portafolio.vientosdelsur.shared.dto.shift.EmployeeScheduleDto

fun EmployeeSchedule.toEmployeeScheduleDto() =
    EmployeeScheduleDto(
        workingDays = workingDays.map { it.toShiftDateDto() },
        daysOff = daysOff
    )
