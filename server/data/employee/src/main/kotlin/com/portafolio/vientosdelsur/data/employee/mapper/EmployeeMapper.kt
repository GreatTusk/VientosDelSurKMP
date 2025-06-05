package com.portafolio.vientosdelsur.data.employee.mapper

import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.HousekeeperRole
import com.portafolio.vientosdelsur.core.database.entity.employee.Occupation
import com.portafolio.vientosdelsur.domain.employee.BaseEmployee
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Floor
import com.portafolio.vientosdelsur.domain.user.User
import kotlinx.datetime.DayOfWeek

fun EmployeeEntity.toEmployee(): Employee {
    val baseEmployee = BaseEmployee(
        id = id.value,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = user.phoneNumber,
        dayOff = DayOfWeek(dayOff ?: 7),
        hireDate = hireDate
    )

    val user = User(
        id = this.user.id.value,
        email = user.email,
        photoUrl = if (user.hasPhoto) "/user/profile-picture/${this.user.id.value}" else null,
        phoneNumber = user.phoneNumber,
        isEnabled = user.isEnabled,
        createdAt = user.createdAt,
        updatedAt = user.updatedAt,
        name = "${baseEmployee.firstName} ${baseEmployee.lastName}"
    )

    return when (occupation) {
        Occupation.HOUSEKEEPER -> {
            val housekeeper = requireNotNull(housekeeper) { "Invalid state. Housekeepers must be on HousekeeperTable" }
            Employee.Housekeeper(
                data = baseEmployee,
                housekeeperRole = when (housekeeper.housekeeperRole) {
                    HousekeeperRole.KITCHEN -> com.portafolio.vientosdelsur.domain.employee.HousekeeperRole.KITCHEN
                    HousekeeperRole.KITCHEN_SUPPORT -> com.portafolio.vientosdelsur.domain.employee.HousekeeperRole.KITCHEN_SUPPORT
                    HousekeeperRole.ON_CALL -> com.portafolio.vientosdelsur.domain.employee.HousekeeperRole.ON_CALL
                },
                preferredFloor = housekeeper.preferredFloor?.digitToIntOrNull()?.let { Floor(it) },
                userData = user
            )
        }

        Occupation.COOK -> Employee.Cook(data = baseEmployee, userData = user)
        Occupation.HOUSEKEEPER_SUPERVISOR -> Employee.HousekeeperSupervisor(data = baseEmployee, userData = user)
        Occupation.ADMIN -> Employee.Admin(data = baseEmployee, userData = user)
    }
}

internal val Employee.occupationEntity: Occupation
    get() {
        return when (this) {
            is Employee.Admin -> Occupation.ADMIN
            is Employee.Cook -> Occupation.COOK
            is Employee.Housekeeper -> Occupation.HOUSEKEEPER
            is Employee.HousekeeperSupervisor -> Occupation.HOUSEKEEPER_SUPERVISOR
        }
    }

internal fun com.portafolio.vientosdelsur.domain.employee.HousekeeperRole.toHousekeeperRoleEntity() = when (this) {
    com.portafolio.vientosdelsur.domain.employee.HousekeeperRole.KITCHEN -> HousekeeperRole.KITCHEN
    com.portafolio.vientosdelsur.domain.employee.HousekeeperRole.KITCHEN_SUPPORT -> HousekeeperRole.KITCHEN_SUPPORT
    com.portafolio.vientosdelsur.domain.employee.HousekeeperRole.ON_CALL -> HousekeeperRole.ON_CALL
}