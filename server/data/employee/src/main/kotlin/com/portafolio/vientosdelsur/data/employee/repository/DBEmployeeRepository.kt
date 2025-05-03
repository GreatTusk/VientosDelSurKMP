package com.portafolio.vientosdelsur.data.employee.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.core.data.util.suspendTransaction
import com.portafolio.vientosdelsur.data.employee.entity.EmployeeDao
import com.portafolio.vientosdelsur.data.employee.entity.EmployeeEntity
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployeeDto
import com.portafolio.vientosdelsur.shared.dto.EmployeeDto

internal object DBEmployeeRepository : EmployeeRepository {
    override suspend fun allEmployees(): Result<List<EmployeeDto>, DataError.Remote> = suspendTransaction {
        return@suspendTransaction try {
            Result.Success(EmployeeDao.all().map { it.toEmployeeDto() })
        } catch (e: Exception) {
            println(e.message)
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun getEmployeeById(id: Int): Result<EmployeeDto, DataError.Remote> = suspendTransaction {
        return@suspendTransaction try {
            val employee = EmployeeDao.find { (EmployeeEntity.id eq id) }
                .limit(1)
                .map { it.toEmployeeDto() }
                .firstOrNull() ?: return@suspendTransaction Result.Error(DataError.Remote.NOT_FOUND)

            Result.Success(employee)
        } catch (e: Exception) {
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }
}