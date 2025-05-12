package com.portafolio.vientosdelsur.service.employee.mapper

import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.shared.dto.EmployeeDto

fun Employee.toEmployeeDto() = EmployeeDto(
    id = id,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    dayOff = dayOff,
    hireDate = hireDate,
    occupation = occupation
)