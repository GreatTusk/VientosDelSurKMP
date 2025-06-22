package com.portafolio.vientosdelsur.service.shift

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto

typealias MonthlyShiftDistributionResponse = BaseResponseDto<List<MonthlyShiftDistributionDto>>

interface ShiftSchedulerService {
    suspend fun generateDraftSchedule(): Result<MonthlyShiftDistributionResponse, DataError.Remote>
    suspend fun updateDraftSchedule(shifts: List<MonthlyShiftDistributionDto>): Result<MonthlyShiftDistributionResponse, DataError.Remote>
    suspend fun publishSchedule(shifts: List<MonthlyShiftDistributionDto>): EmptyResult<DataError.Remote>
}