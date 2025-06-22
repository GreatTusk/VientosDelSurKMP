package com.portafolio.vientosdelsur.data.shift.repository

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.f776.core.common.throwIfEmpty
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeEntity
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeTable
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftEntity
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.data.shift.mapper.toEmployeeSchedule
import com.portafolio.vientosdelsur.data.shift.mapper.toWorkShiftRow
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeDaysOff
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeSchedule
import com.portafolio.vientosdelsur.domain.shift.model.ShiftDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert

internal class DbShiftRepository(private val defaultDispatcher: CoroutineDispatcher) : ShiftRepository {
    override suspend fun saveAll(
        shifts: Map<EmployeeDaysOff, List<ShiftDate>>
    ): EmptyResult<DataError.Remote> =
        withContext(defaultDispatcher) {
            val rows = shifts.map {
                async { it.toWorkShiftRow() }
            }.awaitAll().flatten()

            return@withContext safeSuspendTransaction {
                WorkShiftTable.batchInsert(rows) {
                    this[WorkShiftTable.employeeId] = it.employeeId
                    this[WorkShiftTable.shift] = it.shift
                    this[WorkShiftTable.date] = it.date
                }
                Unit
            }
        }

    override suspend fun getShiftsDuring(
        employeeId: Int,
        during: ClosedRange<LocalDate>
    ): Result<EmployeeSchedule, DataError.Remote> = safeSuspendTransaction {
        WorkShiftEntity.find {
            (WorkShiftTable.employeeId eq employeeId) and
                    (WorkShiftTable.date.between(during.start, during.endInclusive))
        }.toList().toEmployeeSchedule(during)
    }

    override suspend fun getEmployeesWorkingOn(date: LocalDate): Result<List<Employee>, DataError.Remote> =
        safeSuspendTransaction {
            val query = EmployeeTable.innerJoin(WorkShiftTable)
                .select(EmployeeTable.columns)
                .where {
                    WorkShiftTable.date eq date
                }

            EmployeeEntity.wrapRows(query)
                .toList()
                .map { it.toEmployee() }
                .throwIfEmpty()
        }

    override suspend fun getMonthlyShifts(month: ClosedRange<LocalDate>): Result<Map<Employee, EmployeeSchedule>, DataError.Remote> =
        safeSuspendTransaction {
            WorkShiftEntity.find { WorkShiftTable.date.between(month.start, month.endInclusive) }
                .groupBy { it.employee.toEmployee() }
                .mapValues { (_, shifts) -> shifts.toEmployeeSchedule(month) }
        }

}