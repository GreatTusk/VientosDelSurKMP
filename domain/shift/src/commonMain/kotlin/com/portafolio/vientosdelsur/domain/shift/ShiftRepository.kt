package com.portafolio.vientosdelsur.domain.shift

import com.f776.core.common.DataError
import com.f776.core.common.Result

interface ShiftRepository {
    suspend fun getEmployeeSchedule(employeeId: Int): Result<EmployeeSchedule, DataError.Remote>
}