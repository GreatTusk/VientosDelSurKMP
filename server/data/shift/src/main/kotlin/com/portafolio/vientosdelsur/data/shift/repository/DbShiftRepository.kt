package com.portafolio.vientosdelsur.data.shift.repository

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftEntity
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.data.shift.mapper.toWorkShiftRow
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeDaysOff
import com.portafolio.vientosdelsur.domain.shift.model.ShiftDate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
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

    override suspend fun getMonthlyShiftsFor(
        employeeId: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<Map<EmployeeDaysOff, List<ShiftDate>>, DataError.Remote> {
        TODO("Not yet implemented")
    }

    override suspend fun getEmployeesWorkingOn(date: LocalDate): Result<List<Employee>, DataError.Remote> =
        safeSuspendTransaction {
            WorkShiftEntity.find { WorkShiftTable.date eq date }
                .map { it.employee }
                .map { it.toEmployee() }
        }
}