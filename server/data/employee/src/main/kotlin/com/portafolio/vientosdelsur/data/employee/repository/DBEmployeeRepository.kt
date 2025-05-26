package com.portafolio.vientosdelsur.data.employee.repository

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.f776.core.common.emptyError
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeTable
import com.portafolio.vientosdelsur.core.database.entity.employee.HousekeeperEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.Occupation
import com.portafolio.vientosdelsur.core.database.entity.user.UserEntity
import com.portafolio.vientosdelsur.data.employee.mapper.occupationEntity
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.data.employee.mapper.toHousekeeperRoleEntity
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.HousekeeperRole
import com.portafolio.vientosdelsur.domain.employee.repository.EmployeeRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.toLocalDateTime

internal object DBEmployeeRepository : EmployeeRepository {
    override suspend fun allEmployees(): Result<List<Employee>, DataError.Remote> = safeSuspendTransaction {
        EmployeeEntity.all().map { it.toEmployee() }
    }

    override suspend fun getEmployeeById(id: Int): Result<Employee, DataError.Remote> = safeSuspendTransaction {
        EmployeeEntity.find { (EmployeeTable.id eq id) }
            .firstOrNull()?.toEmployee() ?: emptyError("Employee not found")
    }

    override suspend fun createEmployee(employee: Employee): EmptyResult<DataError.Remote> = safeSuspendTransaction {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val userEntity = UserEntity.new {
            email = employee.userData.email
            photoUrl = employee.userData.photoUrl
            isEnabled = employee.userData.isEnabled
            createdAt = now
            updatedAt = now
        }

        val employeeEntity = EmployeeEntity.new {
            firstName = employee.data.firstName
            lastName = employee.data.lastName
            dayOff = employee.data.dayOff.isoDayNumber
            hireDate = employee.data.hireDate
            occupation = employee.occupationEntity
            user = userEntity
        }

        when(employee) {
            is Employee.Housekeeper -> {
                HousekeeperEntity.new {
                    housekeeperRole = employee.housekeeperRole.toHousekeeperRoleEntity()
                    preferredFloor = employee.preferredFloor?.floor?.digitToChar()
                    this.employee = employeeEntity
                }
            }
            else -> {}
        }
    }
}