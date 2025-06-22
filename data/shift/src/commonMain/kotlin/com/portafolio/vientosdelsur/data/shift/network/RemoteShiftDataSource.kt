package com.portafolio.vientosdelsur.data.shift.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.shift.ScheduleDto

interface RemoteShiftDataSource {
    suspend fun getEmployeeSchedule(employeeId: Int): Result<ScheduleDto, DataError.Remote>

    suspend fun getAllEmployeeSchedule(): Result<List<ScheduleDto>, DataError.Remote>
}