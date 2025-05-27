package com.portafolio.vientosdelsur.service.employee.mapper

import com.portafolio.vientosdelsur.domain.employee.*
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeDto
import com.portafolio.vientosdelsur.shared.dto.employee.EmployeeOccupationDto
import com.portafolio.vientosdelsur.shared.dto.employee.HousekeeperRoleDto
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Employee.toEmployeeDto(): EmployeeDto.Get =
    when (this) {
        is Employee.Housekeeper -> EmployeeDto.Get.Housekeeper(
            id = requireNotNull(data.id) { "Employee must have an id" },
            firstName = data.firstName,
            lastName = data.lastName,
            phoneNumber = data.phoneNumber,
            dayOff = data.dayOff,
            hireDate = data.hireDate,
            role = housekeeperRole.toHousekeeperRoleDto(),
            preferredFloor = preferredFloor?.floor,
            occupation = occupationDto
        )

        else -> EmployeeDto.Get.StandardEmployee(
            id = requireNotNull(data.id) { "Employee must have an id" },
            firstName = data.firstName,
            lastName = data.lastName,
            phoneNumber = data.phoneNumber,
            dayOff = data.dayOff,
            hireDate = data.hireDate,
            occupation = occupationDto
        )
    }

private val Employee.occupationDto: EmployeeOccupationDto
    get() {
        return when (this) {
            is Employee.HousekeeperSupervisor -> EmployeeOccupationDto.HousekeeperSupervisor
            is Employee.Housekeeper -> EmployeeOccupationDto.Housekeeper
            is Employee.Admin -> EmployeeOccupationDto.Admin
            is Employee.Cook -> EmployeeOccupationDto.Cook
        }
    }

internal fun EmployeeDto.Create.toEmployee(): Employee {
    val baseEmployee = BaseEmployee(
        id = null,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        dayOff = dayOff,
        hireDate = hireDate
    )

    val user = User(
        id = userDto.userId,
        email = userDto.email,
        photoUrl = userDto.photoUrl,
        phoneNumber = phoneNumber,
        isEnabled = false,
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        updatedAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    )

    return when (this) {
        is EmployeeDto.Create.Housekeeper -> Employee.Housekeeper(
            data = baseEmployee,
            userData = user,
            housekeeperRole = housekeeperRoleDto.toHousekeeperRole(),
            preferredFloor = null
        )

        is EmployeeDto.Create.StandardEmployee -> {
            when (occupation) {
                EmployeeOccupationDto.HousekeeperSupervisor -> Employee.HousekeeperSupervisor(
                    data = baseEmployee,
                    userData = user,
                )

                EmployeeOccupationDto.Admin -> Employee.HousekeeperSupervisor(
                    data = baseEmployee,
                    userData = user,
                )

                EmployeeOccupationDto.Cook -> Employee.HousekeeperSupervisor(
                    data = baseEmployee,
                    userData = user,
                )

                EmployeeOccupationDto.Housekeeper -> error("Impossible")
            }
        }
    }
}

private fun HousekeeperRoleDto.toHousekeeperRole() =
    when (this) {
        HousekeeperRoleDto.Kitchen -> HousekeeperRole.KITCHEN
        HousekeeperRoleDto.KitchenSupport -> HousekeeperRole.KITCHEN_SUPPORT
        HousekeeperRoleDto.OnCall -> HousekeeperRole.ON_CALL
    }

private fun HousekeeperRole.toHousekeeperRoleDto() =
    when (this) {
        HousekeeperRole.KITCHEN -> HousekeeperRoleDto.Kitchen
        HousekeeperRole.KITCHEN_SUPPORT -> HousekeeperRoleDto.KitchenSupport
        HousekeeperRole.ON_CALL -> HousekeeperRoleDto.OnCall
    }