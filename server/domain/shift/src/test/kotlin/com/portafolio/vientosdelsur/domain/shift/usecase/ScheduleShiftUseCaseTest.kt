package com.portafolio.vientosdelsur.domain.shift.usecase

import com.portafolio.vientosdelsur.domain.employee.Employee
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.DayOfWeek
import kotlin.test.Test

class ScheduleShiftUseCaseTest {

    val employees = listOf(
        Employee(
            id = 1,
            firstName = "Juan",
            lastName = "Pérez",
            phoneNumber = "+56912345678",
            dayOff = DayOfWeek.SUNDAY, // Monday
            hireDate = LocalDate(2021, 3, 15),
            occupation = "Chef"
        ),
        Employee(
            id = 2,
            firstName = "María",
            lastName = "González",
            phoneNumber = "+56923456789",
            dayOff = DayOfWeek.MONDAY, // Wednesday
            hireDate = LocalDate(2022, 6, 8),
            occupation = "Waiter"
        ),
        Employee(
            id = 3,
            firstName = "Carlos",
            lastName = "Rodríguez",
            phoneNumber = "+56934567890",
            dayOff = DayOfWeek.FRIDAY, // Friday
            hireDate = LocalDate(2020, 11, 20),
            occupation = "Bartender"
        ),
        Employee(
            id = 4,
            firstName = "Ana",
            lastName = "Martínez",
            phoneNumber = "+56945678901",
            dayOff = DayOfWeek.SATURDAY, // Tuesday
            hireDate = LocalDate(2023, 1, 10),
            occupation = "Host"
        ),
        Employee(
            id = 5,
            firstName = "Diego",
            lastName = "Sánchez",
            phoneNumber = "+56956789012",
            dayOff = DayOfWeek.THURSDAY, // Thursday
            hireDate = LocalDate(2022, 9, 5),
            occupation = "Kitchen Assistant"
        )
    )

    @Test
    fun `assigns two sundays off`() {
        ScheduleShiftUseCase().assignSundaysOff(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date, employees = employees
        ).forEach(::println)
    }
}