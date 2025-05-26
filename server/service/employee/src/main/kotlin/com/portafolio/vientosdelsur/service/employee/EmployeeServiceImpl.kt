package com.portafolio.vientosdelsur.service.employee

import com.f776.core.common.*
import com.portafolio.vientosdelsur.domain.employee.repository.EmployeeRepository
import com.portafolio.vientosdelsur.service.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.employee.CreateEmployeeDto

internal class EmployeeServiceImpl(private val employeeRepository: EmployeeRepository) : EmployeeService {
    override suspend fun getAllEmployees(): Result<EmployeeListResponse, DataError.Remote> {
        return employeeRepository.allEmployees()
            .flatMap { it.toEmployeeDto() }
            .map {
                BaseResponseDto(
                    message = "Retrieved successfully",
                    data = it
                )
            }
    }

    override suspend fun getEmployeeById(id: Int): Result<EmployeeResponse, DataError.Remote> {
        return employeeRepository.getEmployeeById(id = id)
            .map {
                BaseResponseDto(
                    message = "Retrieved successfully",
                    data = it.toEmployeeDto()
                )
            }
    }

    override suspend fun createEmployee(employeeDto: CreateEmployeeDto): EmptyResult<DataError.Remote> {
        TODO("Not yet implemented")
    }
}