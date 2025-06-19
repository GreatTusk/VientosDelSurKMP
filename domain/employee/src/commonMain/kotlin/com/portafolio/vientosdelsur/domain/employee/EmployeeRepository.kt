package com.portafolio.vientosdelsur.domain.employee

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result

interface EmployeeRepository {
    suspend fun getEmployeeByUserId(userId: String): Result<Employee, DataError>
    suspend fun getEmployees(): Result<List<Employee>, DataError>
    suspend fun getEmployeesToday(): Result<List<Employee>, DataError>
    suspend fun isUserActive(userId: String): Boolean

    suspend fun createEmployee(employee: CreateEmployee): EmptyResult<DataError.Remote>
}