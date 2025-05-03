package com.portafolio.vientosdelsur.data.employee.repository

import com.portafolio.vientosdelsur.shared.dto.EmployeeDto

interface EmployeeRepository {
    suspend fun allEmployees(): List<EmployeeDto>
}