package com.portafolio.vientosdelsur.data.shift.mapper

import com.portafolio.vientosdelsur.core.database.entity.work.Shift
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeDaysOff
import com.portafolio.vientosdelsur.domain.shift.model.ShiftDate
import kotlinx.datetime.LocalDate

internal data class WorkShiftRow(
    val employeeId: Int,
    val date: LocalDate,
    val shift: Shift
)

internal fun Map.Entry<EmployeeDaysOff, List<ShiftDate>>.toWorkShiftRow() = value.map { shiftDate ->
    val id = checkNotNull(key.employee.data.id) { "Employee must have id" }
    WorkShiftRow(
        employeeId = id,
        date = shiftDate.date,
        shift = shiftDate.shift.toEntityShift()
    )
}


internal fun com.portafolio.vientosdelsur.domain.shift.model.Shift.toEntityShift() = when (this) {
    com.portafolio.vientosdelsur.domain.shift.model.Shift.GENERAL_DUTY -> Shift.GENERAL_DUTY
    com.portafolio.vientosdelsur.domain.shift.model.Shift.KITCHEN_ASSISTANT -> Shift.KITCHEN_ASSISTANT
    com.portafolio.vientosdelsur.domain.shift.model.Shift.KITCHEN_LEAD -> Shift.KITCHEN_LEAD
}
