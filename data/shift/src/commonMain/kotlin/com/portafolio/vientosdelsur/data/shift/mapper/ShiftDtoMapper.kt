package com.portafolio.vientosdelsur.data.shift.mapper

import com.portafolio.vientosdelsur.domain.shift.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.Shift
import com.portafolio.vientosdelsur.domain.shift.ShiftDate
import com.portafolio.vientosdelsur.domain.shift.ShiftType
import com.portafolio.vientosdelsur.shared.dto.room.ShiftDateDto
import com.portafolio.vientosdelsur.shared.dto.room.ShiftDto
import com.portafolio.vientosdelsur.shared.dto.room.ShiftTypeDto
import com.portafolio.vientosdelsur.shared.dto.shift.ScheduleDto

internal fun ScheduleDto.toDomain(): EmployeeSchedule = EmployeeSchedule(
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
    type = type.toShiftType()
)

private fun ShiftTypeDto.toShiftType() = when(this) {
    ShiftTypeDto.GENERAL_DUTY -> ShiftType.GENERAL_DUTY
    ShiftTypeDto.KITCHEN_ASSISTANT -> ShiftType.KITCHEN_ASSISTANT
    ShiftTypeDto.KITCHEN_LEAD -> ShiftType.KITCHEN_LEAD
}