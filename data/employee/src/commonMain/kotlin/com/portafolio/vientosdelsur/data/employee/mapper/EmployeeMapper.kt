package com.portafolio.vientosdelsur.data.employee.mapper

import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeOccupationDto

fun EmployeeDto.Get.toEmployee(): Employee {
    Employee(
        id = id,
        firstName = firstName,
        lastName = lastName,
        occupation = occupation.toOccupation(),
        userId = TODO(),
        email = TODO(),
        photoUrl = TODO(),
        phoneNumber = phoneNumber,
        isEnabled = TODO()
    )

    TODO()
}

private fun EmployeeOccupationDto.toOccupation() = when (this) {
    EmployeeOccupationDto.HousekeeperSupervisor -> Occupation.SUPERVISOR
    EmployeeOccupationDto.Housekeeper -> Occupation.HOUSEKEEPER
    EmployeeOccupationDto.Admin -> Occupation.ADMIN
    EmployeeOccupationDto.Cook -> Occupation.OTHER
}