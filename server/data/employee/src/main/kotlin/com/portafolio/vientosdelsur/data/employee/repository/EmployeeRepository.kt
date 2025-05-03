package com.portafolio.vientosdelsur.data.employee.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.EmployeeDto

interface EmployeeRepository {
    suspend fun allEmployees(): Result<List<EmployeeDto>, DataError.Remote>
    suspend fun getEmployeeById(id: Int): Result<EmployeeDto, DataError.Remote>
}