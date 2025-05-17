package com.portafolio.vientosdelsur.data.shift.repository

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.core.database.entity.employee.EmployeeTable
import com.portafolio.vientosdelsur.core.database.entity.work.Shift
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftEntity
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.data.shift.mapper.toEmployee
import com.portafolio.vientosdelsur.data.shift.mapper.toWorkShiftRow
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.shift.ShiftRepository
import com.portafolio.vientosdelsur.domain.shift.model.EmployeeDaysOff
import com.portafolio.vientosdelsur.domain.shift.model.HousekeeperShift
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

    override suspend fun getMonthlyShifts(
        startDate: LocalDate,
        endDate: LocalDate,
        occupation: Occupation
    ): Result<Map<LocalDate, List<HousekeeperShift>>, DataError.Remote> = safeSuspendTransaction {
        val occupationEntity = when (occupation) {
            Occupation.HOUSEKEEPER -> com.portafolio.vientosdelsur.core.database.entity.employee.Occupation.HOUSEKEEPER
            Occupation.SUPERVISOR -> com.portafolio.vientosdelsur.core.database.entity.employee.Occupation.HOUSEKEEPER_SUPERVISOR
            Occupation.ADMIN -> com.portafolio.vientosdelsur.core.database.entity.employee.Occupation.ADMIN
            Occupation.COOK -> com.portafolio.vientosdelsur.core.database.entity.employee.Occupation.COOK
        }

        val shifts = WorkShiftEntity.find {
            (WorkShiftTable.date.between(
                startDate,
                endDate
            ))
        }.filter {
            it.employee.occupation == occupationEntity
        }.map {
            val shift = HousekeeperShift(
                employee = it.employee.toEmployee() as Employee.Housekeeper,
                workMinutes = when (it.shift) {
                    Shift.GENERAL_DUTY -> FULL_TIME_HOURS * 60
                    Shift.KITCHEN_ASSISTANT -> MIXED_KITCHEN_HOURS * 60
                    Shift.KITCHEN_LEAD -> MIXED_KITCHEN_HOURS * 60
                }
            )
            it.date to shift
        }

        val groupedShifts = shifts.groupBy({ it.first }, { it.second })
        groupedShifts
    }


    override suspend fun getMonthlyShiftsFor(
        employeeId: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<Map<EmployeeDaysOff, List<ShiftDate>>, DataError.Remote> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val FULL_TIME_HOURS = 7
        private const val MIXED_KITCHEN_HOURS = 3
    }

}