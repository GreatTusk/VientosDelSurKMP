package com.portafolio.vientosdelsur.service.shift

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.portafolio.vientosdelsur.domain.shift.usecase.ScheduleShiftUseCase
import com.portafolio.vientosdelsur.service.shift.mapper.toMonthlyShiftsDto
import com.portafolio.vientosdelsur.shared.dto.MonthlyShiftDistributionDto
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class ShiftServiceImpl(private val scheduleShiftUseCase: ScheduleShiftUseCase) : ShiftService {
    override suspend fun scheduleShifts(): Result<List<MonthlyShiftDistributionDto>, DataError.Remote> {
        return scheduleShiftUseCase(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
            .map { shifts ->
                shifts.map { it.toMonthlyShiftsDto() }
            }
    }
}