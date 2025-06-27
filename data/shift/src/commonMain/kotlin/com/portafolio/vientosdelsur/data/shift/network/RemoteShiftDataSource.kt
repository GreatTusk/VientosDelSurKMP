package com.portafolio.vientosdelsur.data.shift.network

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.room.MonthlyShiftDistributionDto
import com.portafolio.vientosdelsur.shared.dto.shift.ScheduleDto
import kotlinx.datetime.LocalDate

interface RemoteShiftDataSource {
    suspend fun getEmployeeSchedule(employeeId: Int): Result<ScheduleDto, DataError.Remote>
    suspend fun getAllEmployeeSchedule(date: LocalDate): Result<List<MonthlyShiftDistributionDto>, DataError.Remote>

    suspend fun generateShiftScheduleDraft(): Result<List<MonthlyShiftDistributionDto>, DataError.Remote>
    suspend fun saveShiftScheduleDraft(shiftSchedule: List<MonthlyShiftDistributionDto>): EmptyResult<DataError.Remote>
}