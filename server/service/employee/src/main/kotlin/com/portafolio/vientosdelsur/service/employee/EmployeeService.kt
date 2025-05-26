package com.portafolio.vientosdelsur.service.employee

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto

typealias EmployeeListResponse = BaseResponseDto<List<EmployeeDto.Get>>
typealias EmployeeResponse = BaseResponseDto<EmployeeDto.Get>

interface EmployeeService {
    suspend fun getAllEmployees(): Result<EmployeeListResponse, DataError.Remote>
    suspend fun getEmployeeById(id: Int): Result<EmployeeResponse, DataError.Remote>
    suspend fun createEmployee(employeeDto: EmployeeDto.Create): EmptyResult<DataError.Remote>
}