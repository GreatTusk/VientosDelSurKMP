package com.portafolio.vientosdelsur.service.shift

import com.f776.core.common.*
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import com.portafolio.vientosdelsur.domain.shift.usecase.ScheduleShiftUseCase
import com.portafolio.vientosdelsur.service.shift.mapper.toEmployeeShiftsMap
import com.portafolio.vientosdelsur.service.shift.mapper.toMonthlyShiftsDto
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class ShiftSchedulerServiceImpl(
    private val scheduleShiftUseCase: ScheduleShiftUseCase,
    private val shiftRepository: ShiftRepository
) : ShiftSchedulerService {
    override suspend fun generateDraftSchedule(): Result<MonthlyShiftDistributionResponse, DataError.Remote> {
        return scheduleShiftUseCase(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
            .map { shifts -> shifts.map { it.toMonthlyShiftsDto() } }
            .map { BaseResponseDto(message = "Distribution as follows", data = it) }
    }

    override suspend fun updateDraftSchedule(shifts: List<MonthlyShiftDistributionDto>): Result<MonthlyShiftDistributionResponse, DataError.Remote> {
        TODO()
    }

    override suspend fun publishSchedule(shifts: List<MonthlyShiftDistributionDto>): EmptyResult<DataError.Remote> {
        return shiftRepository.saveAll(shifts.toEmployeeShiftsMap())
    }
}