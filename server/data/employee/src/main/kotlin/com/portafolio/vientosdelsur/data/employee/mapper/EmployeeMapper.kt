package com.portafolio.vientosdelsur.data.employee.mapper

import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.HousekeeperRole
import com.portafolio.vientosdelsur.core.database.entity.employee.Occupation
import com.portafolio.vientosdelsur.domain.employee.BaseEmployee
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Floor
import kotlinx.datetime.DayOfWeek

internal fun EmployeeEntity.toEmployee(): Employee {
    val baseEmployee = BaseEmployee(
        id = id.value,
        firstName = firstName,
        lastName = lastName,
        phoneNumber = phoneNumber,
        dayOff = DayOfWeek(dayOff ?: 7),
        hireDate = hireDate.date,
    )

    return when (occupation) {
        Occupation.HOUSEKEEPER -> {
            val housekeeper = requireNotNull(housekeeper) { "Invalid state. Housekeepers must be on HousekeeperTable" }
            println(housekeeper.preferredFloor)
            Employee.Housekeeper(
                data = baseEmployee,
                housekeeperRole = when (housekeeper.housekeeperRole) {
                    HousekeeperRole.KITCHEN -> com.portafolio.vientosdelsur.domain.employee.HousekeeperRole.KITCHEN
                    HousekeeperRole.KITCHEN_SUPPORT -> com.portafolio.vientosdelsur.domain.employee.HousekeeperRole.KITCHEN_SUPPORT
                    HousekeeperRole.ON_CALL -> com.portafolio.vientosdelsur.domain.employee.HousekeeperRole.ON_CALL
                },
                preferredFloor = housekeeper.preferredFloor?.digitToIntOrNull()?.let { Floor(it) }
            )
        }

        Occupation.COOK -> Employee.Cook(data = baseEmployee)
        Occupation.HOUSEKEEPER_SUPERVISOR -> Employee.HousekeeperSupervisor(data = baseEmployee)
        Occupation.ADMIN -> Employee.Admin(data = baseEmployee)
    }
}
