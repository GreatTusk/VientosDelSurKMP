package com.portafolio.vientosdelsur.data.employee.mapper

import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.shared.dto.EmployeeDto

internal fun EmployeeEntity.toEmployeeDto() = EmployeeDto(
    id = id.value,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    dayOff = dayOff ?: 7,
    hireDate = hireDate.toString(),
    occupation = occupation.name
)