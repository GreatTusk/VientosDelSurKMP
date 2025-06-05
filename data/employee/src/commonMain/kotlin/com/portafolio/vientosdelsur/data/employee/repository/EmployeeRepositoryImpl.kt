package com.portafolio.vientosdelsur.data.employee.repository

import com.f776.core.common.*
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.data.employee.network.RemoteEmployeeDataSource
import com.portafolio.vientosdelsur.domain.employee.CreateEmployee
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import com.portafolio.vientosdelsur.domain.employee.UploadPhoto

internal class EmployeeRepositoryImpl(private val remoteEmployeeDataSource: RemoteEmployeeDataSource) :
    EmployeeRepository {
    override suspend fun getEmployee(userId: String): Result<Employee, DataError> {
        return remoteEmployeeDataSource.getEmployeeByUserId(userId)
            .map { it.toEmployee() }
    }

    override suspend fun getEmployees(): Result<List<Employee>, DataError> {
        return remoteEmployeeDataSource.getAllEmployees()
            .flatMap { it.toEmployee() }
    }

    override suspend fun isUserActive(userId: String): Boolean {
        return remoteEmployeeDataSource.isUserActive(userId) is Result.Success
    }

    override suspend fun createEmployee(employee: CreateEmployee): EmptyResult<DataError.Remote> {
        val photo = employee.uploadPhoto as? UploadPhoto.Raw
        return remoteEmployeeDataSource.createEmployee(
            employeeDto = employee.toEmployeeDto(),
            profilePhoto = photo?.byteArray
        )
    }
}