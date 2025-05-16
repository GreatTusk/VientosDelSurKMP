package com.portafolio.vientosdelsur.service.shift.mapper

import com.portafolio.vientosdelsur.domain.shift.model.EmployeeDaysOff
import com.portafolio.vientosdelsur.domain.shift.model.Shift
import com.portafolio.vientosdelsur.domain.shift.model.ShiftDate
import com.portafolio.vientosdelsur.service.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.shared.dto.MonthlyShiftDistributionDto
import com.portafolio.vientosdelsur.shared.dto.ShiftDateDto
import com.portafolio.vientosdelsur.shared.dto.ShiftDto

internal fun Map.Entry<EmployeeDaysOff, List<ShiftDate>>.toMonthlyShiftsDto() = MonthlyShiftDistributionDto(
    employee = key.employee.toEmployeeDto(),
    sundaysOff = key.sundaysOff.daysOff,
    shiftDate = value.map { it.toShiftDateDto() }
)


private fun ShiftDate.toShiftDateDto() = ShiftDateDto(
    shift = shift.toShiftDto(),
    date = date
)


private fun Shift.toShiftDto() = ShiftDto(
    startTime = startTime,
    endTime = endTime,
    name = name
)
