package com.portafolio.vientosdelsur.data.employee.mapper

import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.network.BuildConfig
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeOccupationDto

fun EmployeeDto.Get.toEmployee() =
    Employee(
        id = id,
        firstName = firstName,
        lastName = lastName,
        occupation = occupation.toOccupation(),
        userId = userId,
        email = email,
        photoUrl = photoUrl?.let { "${BuildConfig.BASE_URL}$it" },
        phoneNumber = phoneNumber,
        isEnabled = isEnabled
    )


private fun EmployeeOccupationDto.toOccupation() = when (this) {
    EmployeeOccupationDto.HousekeeperSupervisor -> Occupation.SUPERVISOR
    EmployeeOccupationDto.Housekeeper -> Occupation.HOUSEKEEPER
    EmployeeOccupationDto.Admin -> Occupation.ADMIN
    EmployeeOccupationDto.Cook -> Occupation.COOK
}