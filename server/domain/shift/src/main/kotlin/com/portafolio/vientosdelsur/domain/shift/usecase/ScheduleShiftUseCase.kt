package com.portafolio.vientosdelsur.domain.shift.usecase

import com.portafolio.vientosdelsur.domain.shift.workingDays
import kotlinx.datetime.LocalDate

class ScheduleShiftUseCase {
    fun scheduleMonthlyShifts(month: LocalDate) {
        month.workingDays.map {  }
    }
}