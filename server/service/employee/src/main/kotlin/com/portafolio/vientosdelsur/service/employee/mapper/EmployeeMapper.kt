package com.portafolio.vientosdelsur.service.employee.mapper

import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.shared.dto.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.RoleDataDto

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
    },
    role = if (this is Employee.Housekeeper) {
        RoleDataDto(this.housekeeperRole.name)
    } else {
        null
    }
)