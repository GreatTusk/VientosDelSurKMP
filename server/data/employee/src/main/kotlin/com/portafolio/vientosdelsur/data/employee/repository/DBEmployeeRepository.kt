package com.portafolio.vientosdelsur.data.employee.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeTable
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.repository.EmployeeRepository

internal object DBEmployeeRepository : EmployeeRepository {
    override suspend fun allEmployees(): Result<List<Employee>, DataError.Remote> = safeSuspendTransaction {
        return@safeSuspendTransaction try {
            Result.Success(EmployeeEntity.all().map { it.toEmployeeDto() })
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun getEmployeeById(id: Int): Result<Employee, DataError.Remote> = safeSuspendTransaction {
        return@safeSuspendTransaction try {
            val employee = EmployeeEntity.find { (EmployeeTable.id eq id) }
                .limit(1)
                .map { it.toEmployeeDto() }
                .firstOrNull() ?: return@safeSuspendTransaction Result.Error(DataError.Remote.NOT_FOUND)

            Result.Success(employee)
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }
}