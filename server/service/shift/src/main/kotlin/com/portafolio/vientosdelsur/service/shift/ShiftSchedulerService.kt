package com.portafolio.vientosdelsur.service.shift

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto
import kotlinx.datetime.LocalDate

typealias MonthlyShiftDistributionResponse = BaseResponseDto<List<MonthlyShiftDistributionDto>>

interface ShiftSchedulerService {
    suspend fun generateDraftSchedule(month: LocalDate): Result<MonthlyShiftDistributionResponse, DataError.Remote>
    suspend fun publishSchedule(shifts: List<MonthlyShiftDistributionDto>): EmptyResult<DataError.Remote>
}