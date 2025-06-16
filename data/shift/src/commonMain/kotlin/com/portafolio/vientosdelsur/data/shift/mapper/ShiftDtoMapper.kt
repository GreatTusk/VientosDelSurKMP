package com.portafolio.vientosdelsur.data.shift.mapper

import com.portafolio.vientosdelsur.domain.shift.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.Shift
import com.portafolio.vientosdelsur.domain.shift.ShiftDate
import com.portafolio.vientosdelsur.shared.dto.room.ShiftDateDto
import com.portafolio.vientosdelsur.shared.dto.room.ShiftDto
import com.portafolio.vientosdelsur.shared.dto.shift.EmployeeScheduleDto

internal fun EmployeeScheduleDto.toDomain(): EmployeeSchedule = EmployeeSchedule(
    workingDays = workingDays.map { it.toDomain() },
    daysOff = daysOff
)

private fun ShiftDateDto.toDomain(): ShiftDate = ShiftDate(
    shift = shift.toDomain(),
    date = date
)

private fun ShiftDto.toDomain(): Shift = Shift(
    startTime = startTime,
    endTime = endTime,
    type = type.name
)
