package com.portafolio.vientosdelsur.data.employee.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import com.portafolio.vientosdelsur.domain.employee.EmployeeRole

internal class EmployeeRepositoryImpl : EmployeeRepository {
    override suspend fun getEmployee(): Result<Employee, DataError> {
        return Result.Success(
            Employee(
                id = 1,
                firstName = "Flor",
                lastName = "Muñoz",
                role = EmployeeRole.HOUSEKEEPER
            )
        )
    }
}