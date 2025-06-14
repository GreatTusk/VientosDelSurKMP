package com.portafolio.vientosdelsur.data.housekeeping.repository

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.f776.core.common.throwIfEmpty
import com.portafolio.vientosdelsur.core.database.entity.work.HousekeeperShiftRoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.Shift
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftEntity
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.data.employee.mapper.toEmployee
import com.portafolio.vientosdelsur.data.housekeeping.mapper.toHousekeeperShiftEntities
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.Occupation
import com.portafolio.vientosdelsur.domain.housekeeping.HousekeeperShiftRepository
import com.portafolio.vientosdelsur.domain.housekeeping.model.HousekeeperShift
import com.portafolio.vientosdelsur.domain.housekeeping.usecase.MonthlyRoomDistribution
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.batchInsert

internal class DbHousekeeperShiftRepository(
    private val defaultDispatcher: CoroutineDispatcher
) : HousekeeperShiftRepository {
    override suspend fun saveAll(distribution: MonthlyRoomDistribution): EmptyResult<DataError.Remote> =
        withContext(defaultDispatcher) {
            val shiftDistribution = distribution.toHousekeeperShiftEntities()

            return@withContext safeSuspendTransaction {
                HousekeeperShiftRoomTable.batchInsert(shiftDistribution) {
                    this[HousekeeperShiftRoomTable.roomId] = it.roomId
                    this[HousekeeperShiftRoomTable.wordShiftId] = it.wordShiftId
                    this[HousekeeperShiftRoomTable.roomCleaningType] = it.roomCleaningType
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
                workShiftId = it.id.value,
                employee = it.employee.toEmployee() as Employee.Housekeeper,
                workMinutes = when (it.shift) {
                    Shift.GENERAL_DUTY -> FULL_TIME_HOURS * 60
                    Shift.KITCHEN_ASSISTANT -> MIXED_KITCHEN_HOURS * 60
                    Shift.KITCHEN_LEAD -> MIXED_KITCHEN_HOURS * 60
                }
            )
            it.date to shift
        }.throwIfEmpty()

        val groupedShifts = shifts.groupBy({ it.first }, { it.second })
        groupedShifts
    }

    companion object {
        private const val FULL_TIME_HOURS = 7
        private const val MIXED_KITCHEN_HOURS = 3
    }
}