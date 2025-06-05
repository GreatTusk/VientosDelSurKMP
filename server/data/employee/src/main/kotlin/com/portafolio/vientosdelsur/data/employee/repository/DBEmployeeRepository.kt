package com.portafolio.vientosdelsur.data.employee.repository

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.f776.core.common.emptyError
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeTable
import com.portafolio.vientosdelsur.core.database.entity.employee.HousekeeperEntity
import com.portafolio.vientosdelsur.core.database.entity.user.UserEntity
import com.portafolio.vientosdelsur.core.database.entity.user.UserTable
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.data.employee.mapper.occupationEntity
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.data.employee.mapper.toHousekeeperRoleEntity
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.repository.EmployeeRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.update

internal object DBEmployeeRepository : EmployeeRepository {
    override suspend fun allEmployees(): Result<List<Employee>, DataError.Remote> = safeSuspendTransaction {
        EmployeeEntity.all().map { it.toEmployee() }
    }

    override suspend fun getEmployeeById(id: Int): Result<Employee, DataError.Remote> = safeSuspendTransaction {
        EmployeeEntity.find { (EmployeeTable.id eq id) }
            .firstOrNull()?.toEmployee() ?: emptyError("Employee not found")
    }

    override suspend fun getProfilePicture(userId: String): Result<ByteArray, DataError.Remote> =
        safeSuspendTransaction {
            val file = UserTable.select(UserTable.profilePicture)
                .where { UserTable.id eq userId }
                .firstOrNull() ?: emptyError("User not found")

            val photo = file[UserTable.profilePicture] ?: emptyError("Column not found")
            photo.bytes
        }

    override suspend fun updateProfilePicture(
        userId: String,
        profilePicture: ByteArray
    ): EmptyResult<DataError.Remote> = safeSuspendTransaction {
        UserTable.update(where = { UserTable.id eq userId }) {
            it[UserTable.profilePicture] = ExposedBlob(profilePicture)
        }
    }

    override suspend fun getEmployeeByUserId(userId: String): Result<Employee, DataError.Remote> =
        safeSuspendTransaction {
            UserEntity.findById(userId)?.employee?.toEmployee() ?: emptyError("Employee not found")
        }

    override suspend fun createEmployee(employee: Employee, profilePictureBytes: ByteArray?): EmptyResult<DataError.Remote> = safeSuspendTransaction {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val userEntity = UserEntity.new(id = employee.userData.id) {
            email = employee.userData.email
            phoneNumber = employee.userData.phoneNumber
            isEnabled = true
            createdAt = now
            updatedAt = now
            profilePictureBytes?.let {
                profilePicture = ExposedBlob(it)
            }
        }

        val employeeEntity = EmployeeEntity.new {
            firstName = employee.data.firstName
            lastName = employee.data.lastName
            dayOff = employee.data.dayOff.isoDayNumber
            hireDate = employee.data.hireDate
            occupation = employee.occupationEntity
            user = userEntity
        }

        when (employee) {
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

    override suspend fun isEmployeeActive(userId: String): EmptyResult<DataError.Remote> = safeSuspendTransaction {
        val isEnabled = UserTable.select(UserTable.isEnabled)
            .where { UserTable.id eq userId }
            .firstOrNull()
            ?.let { it[UserTable.isEnabled] } == true

        if (!isEnabled) {
            emptyError("Not found")
        }

        Unit
    }
}