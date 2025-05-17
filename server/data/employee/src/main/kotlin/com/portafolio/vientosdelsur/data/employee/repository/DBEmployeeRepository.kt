package com.portafolio.vientosdelsur.data.employee.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.emptyError
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeTable
import com.portafolio.vientosdelsur.core.database.entity.employee.HousekeeperEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.HousekeeperTable
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.repository.EmployeeRepository
import org.jetbrains.exposed.sql.selectAll

internal object DBEmployeeRepository : EmployeeRepository {
    override suspend fun allEmployees(): Result<List<Employee>, DataError.Remote> = safeSuspendTransaction {
        EmployeeEntity.all().orderBy().map { it.toEmployee() }
    }

    override suspend fun getEmployeeById(id: Int): Result<Employee, DataError.Remote> = safeSuspendTransaction {
        EmployeeEntity.find { (EmployeeTable.id eq id) }
            .firstOrNull()?.toEmployee() ?: emptyError("Employee not found")
    }
}