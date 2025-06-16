package com.portafolio.vientosdelsur.data.shift.network

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.shift.EmployeeScheduleDto

interface RemoteShiftDataSource {
    suspend fun getEmployeeSchedule(employeeId: Int): Result<EmployeeScheduleDto, DataError.Remote>
}