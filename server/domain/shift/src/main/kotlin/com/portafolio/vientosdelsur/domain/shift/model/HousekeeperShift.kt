package com.portafolio.vientosdelsur.domain.shift.model

import com.portafolio.vientosdelsur.domain.employee.Employee

data class HousekeeperShift(
    val employee: Employee.Housekeeper,
    val workMinutes: Int
)
