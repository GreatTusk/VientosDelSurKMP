package com.portafolio.vientosdelsur.domain.shift

import com.f776.core.common.DataError
import com.f776.core.common.Result
import kotlinx.datetime.LocalDate

interface ShiftRepository {
    suspend fun getEmployeeSchedule(employeeId: Int): Result<Schedule, DataError.Remote>

    suspend fun getAllEmployeeSchedule(date: LocalDate): Result<List<EmployeeSchedule>, DataError.Remote>
}