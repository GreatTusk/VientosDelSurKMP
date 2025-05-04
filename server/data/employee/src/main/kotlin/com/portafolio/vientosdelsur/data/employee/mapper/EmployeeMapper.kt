package com.portafolio.vientosdelsur.data.employee.mapper

import com.portafolio.vientosdelsur.data.employee.entity.EmployeeDao
import com.portafolio.vientosdelsur.shared.dto.EmployeeDto

internal fun EmployeeDao.toEmployeeDto() = EmployeeDto(
    id = id.value,
    firstName = firstName,
    lastName = lastName,
    phoneNumber = phoneNumber,
    dayOff = dayOff?.toDayOfWeek() ?: 7,
    hireDate = hireDate.toString(),
    occupation = occupation
)

private fun String.toDayOfWeek(): Int {
    return when (this) {
        "Lunes".lowercase() -> 1
        "Martes".lowercase() -> 2
        "Miércoles".lowercase() -> 3
        "Jueves".lowercase() -> 4
        "Viernes".lowercase() -> 5
        "Sábado".lowercase() -> 6
        "Domingo".lowercase() -> 7
        else -> error("Invalid day of the week: $this")
    }
}