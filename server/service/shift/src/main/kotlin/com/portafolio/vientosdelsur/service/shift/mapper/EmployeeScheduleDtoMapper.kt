package com.portafolio.vientosdelsur.service.shift.mapper

import com.portafolio.vientosdelsur.domain.shift.model.EmployeeDaysOff
import com.portafolio.vientosdelsur.domain.shift.model.Shift
import com.portafolio.vientosdelsur.domain.shift.model.ShiftDate
import com.portafolio.vientosdelsur.domain.shift.model.SundaysOff
import com.portafolio.vientosdelsur.service.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto
import com.portafolio.vientosdelsur.shared.dto.room.ShiftDateDto
import com.portafolio.vientosdelsur.shared.dto.room.ShiftDto
import com.portafolio.vientosdelsur.shared.dto.room.ShiftTypeDto

internal fun List<MonthlyShiftDistributionDto>.toEmployeeShiftsMap(): Map<EmployeeDaysOff, List<ShiftDate>> {
    return associate { monthlyShiftDto ->
        val employeeDaysOff = monthlyShiftDto.toEmployeeDaysOff()
        val shiftDates = monthlyShiftDto.scheduleDto.workingDays.map { it.toDomainShiftDate() }
        employeeDaysOff to shiftDates
    }
}

internal fun MonthlyShiftDistributionDto.toEmployeeDaysOff(): EmployeeDaysOff {
    check(this.scheduleDto.daysOff.size == 2)
    return EmployeeDaysOff(
        employee = employee.toEmployee(),
        sundaysOff = SundaysOff(this.scheduleDto.daysOff.first(), this.scheduleDto.daysOff.last())
    )
}

internal fun ShiftDateDto.toDomainShiftDate() = ShiftDate(
    shift = this.shift.toDomainShift(),
    date = this.date
)


internal fun ShiftDto.toDomainShift() = when (this.type) {
    ShiftTypeDto.GENERAL_DUTY -> Shift.GENERAL_DUTY
    ShiftTypeDto.KITCHEN_ASSISTANT -> Shift.KITCHEN_ASSISTANT
    ShiftTypeDto.KITCHEN_LEAD -> Shift.KITCHEN_LEAD
}