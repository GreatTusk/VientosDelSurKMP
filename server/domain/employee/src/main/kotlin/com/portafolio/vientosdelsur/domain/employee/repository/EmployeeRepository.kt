package com.portafolio.vientosdelsur.domain.employee.repository

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.employee.Employee

interface EmployeeRepository {
    suspend fun allEmployees(): Result<List<Employee>, DataError.Remote>
    suspend fun getEmployeeById(id: Int): Result<Employee, DataError.Remote>
    suspend fun createEmployee(employee: Employee): EmptyResult<DataError.Remote>
}