package com.portafolio.vientosdelsur.data.employee.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.portafolio.vientosdelsur.data.employee.network.RemoteEmployeeDataSource
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository

internal class EmployeeRepositoryImpl(private val remoteEmployeeDataSource: RemoteEmployeeDataSource) : EmployeeRepository {
    override suspend fun getEmployee(userId: String): Result<Employee, DataError> {
//        return remoteEmployeeDataSource.getEmployeeByUserId(userId)
        TODO()
    }

    override suspend fun getEmployees(): Result<List<Employee>, DataError> {
        TODO("Not yet implemented")
    }
}