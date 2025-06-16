package com.portafolio.vientosdelsur.data.shift.mapper

import com.portafolio.vientosdelsur.core.database.entity.work.Shift
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftEntity
import com.portafolio.vientosdelsur.domain.shift.dateUntil
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.model.ShiftDate
import kotlinx.datetime.LocalDate

internal fun List<WorkShiftEntity>.toEmployeeSchedule(range: ClosedRange<LocalDate>): EmployeeSchedule {
    val workingDays = map {
        ShiftDate(
            shift = it.shift.toDomainShift(),
            date = it.date
        )
    }

    val workingDates = workingDays.map { it.date }.toSet()

    return EmployeeSchedule(
        workingDays = workingDays,
        daysOff = range.start.dateUntil(range.endInclusive)
            .filterNot { day -> day in workingDates }
            .toList()
    )
}

internal fun Shift.toDomainShift() = when (this) {
    Shift.GENERAL_DUTY -> com.portafolio.vientosdelsur.domain.shift.model.Shift.GENERAL_DUTY
    Shift.KITCHEN_ASSISTANT -> com.portafolio.vientosdelsur.domain.shift.model.Shift.KITCHEN_ASSISTANT
    Shift.KITCHEN_LEAD -> com.portafolio.vientosdelsur.domain.shift.model.Shift.KITCHEN_LEAD
}