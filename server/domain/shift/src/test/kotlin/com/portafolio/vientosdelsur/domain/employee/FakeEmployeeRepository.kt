package com.portafolio.vientosdelsur.domain.employee

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.employee.repository.EmployeeRepository
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

class FakeEmployeeRepository: EmployeeRepository {
    override suspend fun allEmployees(): Result<List<Employee>, DataError.Remote> {
        val testEmployees = listOf(
            Employee.Cook(
                BaseEmployee(
                    id = 1,
                    firstName = "Juan",
                    lastName = "Pérez",
                    phoneNumber = "+56912345678",
                    dayOff = DayOfWeek.SUNDAY,
                    hireDate = LocalDate(2021, 3, 15)
                )
            ),
            Employee.Housekeeper(
                data = BaseEmployee(
                    id = 2,
                    firstName = "María",
                    lastName = "González",
                    phoneNumber = "+56923456789",
                    dayOff = DayOfWeek.MONDAY,
                    hireDate = LocalDate(2022, 6, 8)
                ),
                housekeeperRole = HousekeeperRole.KITCHEN,
                preferredFloor = Floor(2)
            ),
            Employee.Housekeeper(
                data = BaseEmployee(
                    id = 3,
                    firstName = "Carlos",
                    lastName = "Rodríguez",
                    phoneNumber = "+56934567890",
                    dayOff = DayOfWeek.FRIDAY,
                    hireDate = LocalDate(2020, 11, 20)
                ),
                housekeeperRole = HousekeeperRole.KITCHEN_SUPPORT,
                preferredFloor = Floor(1)
            ),
            Employee.HousekeeperSupervisor(
                BaseEmployee(
                    id = 4,
                    firstName = "Ana",
                    lastName = "Martínez",
                    phoneNumber = "+56945678901",
                    dayOff = DayOfWeek.SATURDAY,
                    hireDate = LocalDate(2023, 1, 10)
                )
            ),
            Employee.Housekeeper(
                data = BaseEmployee(
                    id = 5,
                    firstName = "Diego",
                    lastName = "Sánchez",
                    phoneNumber = "+56956789012",
                    dayOff = DayOfWeek.THURSDAY,
                    hireDate = LocalDate(2022, 9, 5)
                ),
                housekeeperRole = HousekeeperRole.ON_CALL,
                preferredFloor = null
            )
        )

        return Result.Success(testEmployees)
    }

    override suspend fun getEmployeeById(id: Int): Result<Employee, DataError.Remote> {
        TODO("Not yet implemented")
    }
}