package com.portafolio.vientosdelsur.data.room.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.map
import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatusEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatusTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.HousekeeperShiftRoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftEntity
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import com.portafolio.vientosdelsur.core.database.util.suspendTransaction
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.domain.housekeeping.RoomRepository
import com.portafolio.vientosdelsur.shared.domain.Room
import com.portafolio.vientosdelsur.shared.domain.RoomState
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.*

internal object DBRoomRepository : RoomRepository {
    override suspend fun getAllRooms(): Result<List<Room>, DataError.Remote> = suspendTransaction {
        return@suspendTransaction try {
            Result.Success(RoomEntity.all().map { it.toRoom() })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun getRoomDistributionForHousekeeperOn(
        housekeeperId: Int,
        date: LocalDate
    ): Result<List<RoomState>, DataError.Remote> = suspendTransaction {
        val shift = WorkShiftEntity
            .find { WorkShiftTable.employeeId eq housekeeperId and (WorkShiftTable.date eq date) }
            .firstOrNull() ?: return@suspendTransaction Result.Empty

        val latestState = wrapAsExpression<RoomStatusTable>(
            RoomStatusTable.selectAll()
                .where { RoomStatusTable.roomId eq RoomTable.id }
                .orderBy(RoomStatusTable.updatedAt, SortOrder.DESC)
                .limit(1)
        )


        TODO("Not yet implemented")
    }

    override suspend fun getAllRoomStatusOn(date: LocalDate): Result<List<RoomState>, DataError.Remote> =
        suspendTransaction {
            TODO("Not yet implemented")
        }
}