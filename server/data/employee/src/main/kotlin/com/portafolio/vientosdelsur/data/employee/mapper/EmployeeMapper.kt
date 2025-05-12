package com.portafolio.vientosdelsur.data.employee.mapper

import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.domain.employee.Employee

internal fun EmployeeEntity.toEmployeeDto() = Employee(
    id = id.value,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    dayOff = dayOff ?: 7,
    hireDate = hireDate.date,
    occupation = occupation.name
)