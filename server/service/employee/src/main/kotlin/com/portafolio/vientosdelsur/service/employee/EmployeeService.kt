package com.portafolio.vientosdelsur.service.employee

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.shared.dto.BaseResponseDto
import com.portafolio.vientosdelsur.shared.dto.EmployeeDto

typealias EmployeeListResponse = BaseResponseDto<List<EmployeeDto>>
typealias EmployeeResponse = BaseResponseDto<EmployeeDto>

interface EmployeeService {
    suspend fun getAllEmployees(): Result<BaseResponseDto<List<EmployeeDto>>, DataError.Remote>
    suspend fun getEmployeeById(id: Int): Result<EmployeeResponse, DataError.Remote>
}