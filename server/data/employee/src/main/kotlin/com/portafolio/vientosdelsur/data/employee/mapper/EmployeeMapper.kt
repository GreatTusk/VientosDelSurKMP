package com.portafolio.vientosdelsur.data.employee.mapper

import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeDao
import com.portafolio.vientosdelsur.shared.dto.EmployeeDto

internal fun EmployeeDao.toEmployeeDto() = EmployeeDto(
    id = id.value,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    dayOff = dayOff ?: 7,
    hireDate = hireDate.toString(),
    occupation = occupation.name
)