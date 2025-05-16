package com.portafolio.vientosdelsur.service.shift.mapper

import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.shared.dto.EmployeeDto

fun Employee.toEmployeeDto() = EmployeeDto(
    id = data.id,
    firstName = data.firstName,
    lastName = data.lastName,
    phoneNumber = data.phoneNumber,
    dayOff = data.dayOff,
    hireDate = data.hireDate,
    occupation = when(this) {
        is Employee.HousekeeperSupervisor -> "Supervisor"
        is Employee.Housekeeper -> "Housekeeper"
        is Employee.Admin -> "Admin"
        is Employee.Cook -> "Cook"
    }
)