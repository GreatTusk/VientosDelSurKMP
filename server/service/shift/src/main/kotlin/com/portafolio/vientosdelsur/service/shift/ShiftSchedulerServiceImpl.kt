package com.portafolio.vientosdelsur.service.shift

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.f776.core.common.map
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import com.portafolio.vientosdelsur.domain.shift.usecase.ScheduleShiftUseCase
import com.portafolio.vientosdelsur.service.shift.mapper.toEmployeeShiftsMap
import com.portafolio.vientosdelsur.service.shift.mapper.toMonthlyShiftsDto
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto
import kotlinx.datetime.LocalDate

internal class ShiftSchedulerServiceImpl(
    private val scheduleShiftUseCase: ScheduleShiftUseCase,
    private val shiftRepository: ShiftRepository
) : ShiftSchedulerService {
    override suspend fun generateDraftSchedule(month: LocalDate): Result<MonthlyShiftDistributionResponse, DataError.Remote> {
        return scheduleShiftUseCase(date = month)
            .map { shifts -> shifts.map { it.toMonthlyShiftsDto(month) } }
            .map { BaseResponseDto(message = "Distribution as follows", data = it) }
    }

    override suspend fun publishSchedule(shifts: List<MonthlyShiftDistributionDto>): EmptyResult<DataError.Remote> {
        return shiftRepository.saveAll(shifts.toEmployeeShiftsMap())
    }
}