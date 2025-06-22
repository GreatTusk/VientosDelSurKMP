package com.portafolio.vientosdelsur.domain.shift

import com.f776.core.common.DataError
import com.f776.core.common.Result

interface ShiftRepository {
    suspend fun getEmployeeSchedule(employeeId: Int): Result<Schedule, DataError.Remote>

    suspend fun getAllEmployeeSchedule(): Result<List<EmployeeSchedule>, DataError.Remote>
}