package com.portafolio.vientosdelsur.domain.employee

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate


sealed interface Employee {
    val data: BaseEmployee

    data class HousekeeperSupervisor(override val data: BaseEmployee) : Employee
    data class Admin(override val data: BaseEmployee) : Employee
    data class Cook(override val data: BaseEmployee) : Employee

    data class Housekeeper(
        override val data: BaseEmployee,
        val housekeeperRole: HousekeeperRole,
        val preferredFloor: Floor?
    ) : Employee
}

data class BaseEmployee(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val dayOff: DayOfWeek,
    val hireDate: LocalDate,
)

@JvmInline
value class Floor(val floor: Int) {
    init {
        require(floor in 1..4) { "Floor must be between 1 and 4, got $floor" }
    }
}
