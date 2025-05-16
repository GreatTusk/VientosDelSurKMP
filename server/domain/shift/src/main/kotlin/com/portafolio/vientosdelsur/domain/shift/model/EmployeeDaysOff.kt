package com.portafolio.vientosdelsur.domain.shift.model

import com.portafolio.vientosdelsur.domain.employee.Employee

data class EmployeeDaysOff(
    val employee: Employee,
    val sundaysOff: SundaysOff
)
