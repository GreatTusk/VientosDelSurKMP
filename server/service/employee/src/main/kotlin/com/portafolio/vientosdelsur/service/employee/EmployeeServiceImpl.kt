package com.portafolio.vientosdelsur.service.employee

import com.f776.core.common.*
import com.portafolio.vientosdelsur.domain.employee.repository.EmployeeRepository
import com.portafolio.vientosdelsur.service.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.service.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto

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

    override suspend fun getProfilePicture(userId: String): Result<ByteArray, DataError.Remote> =
        employeeRepository.getProfilePicture(userId)

    override suspend fun updateProfilePicture(
        userId: String,
        profilePicture: ByteArray
    ): EmptyResult<DataError.Remote> = employeeRepository.updateProfilePicture(userId, profilePicture)

    override suspend fun getEmployeeByUserId(userId: String): Result<EmployeeResponse, DataError.Remote> {
        return employeeRepository.getEmployeeByUserId(userId)
            .map {
                BaseResponseDto(
                    message = "Retrieved successfully",
                    data = it.toEmployeeDto()
                )
            }
    }

    override suspend fun createEmployee(employeeDto: EmployeeDto.Create): EmptyResult<DataError.Remote> {
        return employeeRepository.createEmployee(employeeDto.toEmployee())
    }

    override suspend fun isEmployeeActive(userId: String): EmptyResult<DataError.Remote> {
        return employeeRepository.isEmployeeActive(userId)
    }
}