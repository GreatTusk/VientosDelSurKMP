package com.portafolio.vientosdelsur.data.employee.mapper

import com.portafolio.vientosdelsur.domain.employee.CreateEmployee
import com.portafolio.vientosdelsur.domain.employee.HousekeeperRole
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.employee.UploadPhoto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeOccupationDto
import com.portafolio.vientosdelsur.shared.dto.employee.HousekeeperRoleDto
import com.portafolio.vientosdelsur.shared.dto.employee.UserDto

internal fun CreateEmployee.toEmployeeDto(): EmployeeDto.Create {
    return if (housekeeperRole != null) {
        EmployeeDto.Create.Housekeeper(
            housekeeperRoleDto = housekeeperRole!!.toHousekeeperRoleDto(),
            userDto = UserDto(
                userId = userId,
                photoUrl = if (uploadPhoto is UploadPhoto.URL) (uploadPhoto as UploadPhoto.URL).url else null,
                email = email,
                isEnabled = false
            ),
            firstName = firstName,
            lastName = lastName,
            phoneNumber = "",
            dayOff = dayOff,
            hireDate = hireDate,
            occupation = occupation.toOccupationDto()
        )
    } else {
        EmployeeDto.Create.StandardEmployee(
            userDto = UserDto(
                userId = userId,
                photoUrl = if (uploadPhoto is UploadPhoto.URL) (uploadPhoto as UploadPhoto.URL).url else null,
                email = email,
                isEnabled = false
            ),
            firstName = firstName,
            lastName = lastName,
            phoneNumber = "",
            dayOff = dayOff,
            hireDate = hireDate,
            occupation = occupation.toOccupationDto()
        )
    }
}

private fun HousekeeperRole.toHousekeeperRoleDto() = when (this) {
    HousekeeperRole.KITCHEN -> HousekeeperRoleDto.Kitchen
    HousekeeperRole.KITCHEN_SUPPORT -> HousekeeperRoleDto.KitchenSupport
    HousekeeperRole.ON_CALL -> HousekeeperRoleDto.OnCall
}

private fun Occupation.toOccupationDto() = when (this) {
    Occupation.HOUSEKEEPER -> EmployeeOccupationDto.Housekeeper
    Occupation.SUPERVISOR ->  EmployeeOccupationDto.HousekeeperSupervisor
    Occupation.ADMIN ->  EmployeeOccupationDto.Admin
}