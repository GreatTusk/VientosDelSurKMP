package com.portafolio.vientosdelsur.data.employee.network

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import kotlinx.datetime.LocalDate

internal interface RemoteEmployeeDataSource {
    suspend fun getEmployeeByUserId(userId: String): Result<EmployeeDto.Get, DataError.Remote>
    suspend fun getAllEmployees(): Result<List<EmployeeDto.Get>, DataError.Remote>
    suspend fun getEmployeesToday(today: LocalDate): Result<List<EmployeeDto.Get>, DataError.Remote>
    suspend fun isUserActive(userId: String): EmptyResult<DataError.Remote>

    suspend fun createEmployee(employeeDto: EmployeeDto.Create, profilePhoto: ByteArray?): EmptyResult<DataError.Remote>
}