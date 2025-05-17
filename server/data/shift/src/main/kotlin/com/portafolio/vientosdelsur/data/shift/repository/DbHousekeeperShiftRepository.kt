package com.portafolio.vientosdelsur.data.shift.repository

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.portafolio.vientosdelsur.core.database.entity.work.HousekeeperShiftRoomTable
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.data.shift.mapper.toHousekeeperShiftEntities
import com.portafolio.vientosdelsur.domain.housekeeping.HousekeeperShiftRepository
import com.portafolio.vientosdelsur.domain.housekeeping.usecase.MonthlyRoomDistribution
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.batchInsert

internal class DbHousekeeperShiftRepository(
    private val defaultDispatcher: CoroutineDispatcher
) : HousekeeperShiftRepository {
    override suspend fun saveAll(distribution: MonthlyRoomDistribution): EmptyResult<DataError.Remote> = withContext(defaultDispatcher) {
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
}