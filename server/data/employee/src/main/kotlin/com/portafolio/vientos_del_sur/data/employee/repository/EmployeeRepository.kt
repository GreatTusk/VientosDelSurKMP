package com.portafolio.vientos_del_sur.data.employee.repository

import com.portafolio.vientos_del_sur.shared.dto.EmployeeDto

interface EmployeeRepository {
    suspend fun allEmployees(): List<EmployeeDto>
}