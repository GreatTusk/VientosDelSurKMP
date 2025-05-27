package com.portafolio.vientosdelsur.data.employee.network

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto

internal interface RemoteEmployeeDataSource {
    suspend fun getEmployeeByUserId(userId: String): Result<EmployeeDto.Get, DataError.Remote>
    suspend fun getAllEmployees(): Result<List<EmployeeDto.Get>, DataError.Remote>
    suspend fun isUserActive(userId: String): EmptyResult<DataError.Remote>
}