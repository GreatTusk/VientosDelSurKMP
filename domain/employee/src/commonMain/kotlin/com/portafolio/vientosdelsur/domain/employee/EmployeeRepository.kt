package com.portafolio.vientosdelsur.domain.employee

import com.f776.core.common.DataError
import com.f776.core.common.Result

interface EmployeeRepository {
    suspend fun getEmployee(userId: String): Result<Employee, DataError>
    suspend fun getEmployees(): Result<List<Employee>, DataError>
}