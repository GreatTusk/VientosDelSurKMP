package com.portafolio.vientosdelsur.data.employee.mapper

import com.portafolio.vientosdelsur.data.employee.entity.EmployeeDao
import com.portafolio.vientosdelsur.shared.dto.EmployeeDto

internal fun EmployeeDao.toEmployeeDto() = EmployeeDto(
    id = id.value
)