package com.portafolio.vientosdelsur.domain.employee

import com.f776.core.common.DataError
import com.f776.core.common.Result

interface EmployeeRepository {
    suspend fun getEmployee(): Result<Employee, DataError>
}