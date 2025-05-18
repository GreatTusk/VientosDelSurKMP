package com.portafolio.vientosdelsur.domain.housekeeping.model

import com.portafolio.vientosdelsur.domain.employee.Employee

data class HousekeeperShift(
    val workShiftId: Int,
    val employee: Employee.Housekeeper,
    val workMinutes: Int
)
