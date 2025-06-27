package com.portafolio.vientosdelsur.service.shift.mapper

import com.portafolio.vientosdelsur.domain.shift.dateUntil
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeDaysOff
import com.portafolio.vientosdelsur.domain.shift.model.Shift
import com.portafolio.vientosdelsur.domain.shift.model.ShiftDate
import com.portafolio.vientosdelsur.domain.shift.workingDaysRange
import com.portafolio.vientosdelsur.service.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto
import com.portafolio.vientosdelsur.shared.dto.room.ShiftDateDto
import com.portafolio.vientosdelsur.shared.dto.room.ShiftDto
import com.portafolio.vientosdelsur.shared.dto.room.ShiftTypeDto
import com.portafolio.vientosdelsur.shared.dto.shift.ScheduleDto
import kotlinx.datetime.LocalDate

// Causes regression
internal fun Map.Entry<EmployeeDaysOff, List<ShiftDate>>.toMonthlyShiftsDto(month: LocalDate): MonthlyShiftDistributionDto {
    val range = month.workingDaysRange
    val workingDates = value.map { it.date }.toSet()

    return MonthlyShiftDistributionDto(
        employee = key.employee.toEmployeeDto(),
        scheduleDto = ScheduleDto(
            workingDays = value.map { it.toShiftDateDto() },
            daysOff = key.sundaysOff.daysOff.toList() + range.start.dateUntil(range.endInclusive)
                .filterNot { day -> day in workingDates }
                .toList()
        )
    )
}


internal fun ShiftDate.toShiftDateDto() = ShiftDateDto(
    shift = shift.toShiftDto(),
    date = date
)

private fun Shift.toShiftDto() = ShiftDto(
    startTime = startTime,
    endTime = endTime,
    type = when (this) {
        Shift.GENERAL_DUTY -> ShiftTypeDto.GENERAL_DUTY
        Shift.KITCHEN_ASSISTANT -> ShiftTypeDto.KITCHEN_ASSISTANT
        Shift.KITCHEN_LEAD -> ShiftTypeDto.KITCHEN_LEAD
    }
)