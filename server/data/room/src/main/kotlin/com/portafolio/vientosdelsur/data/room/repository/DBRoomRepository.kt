package com.portafolio.vientosdelsur.data.room.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.f776.core.common.emptyError
import com.f776.core.common.throwIfEmpty
import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.HousekeeperShiftRoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.data.room.mapper.mapShiftRoomsToRoomState
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import com.portafolio.vientosdelsur.domain.room.model.Room
import com.portafolio.vientosdelsur.domain.room.model.RoomState
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll

internal object DBRoomRepository : RoomRepository {

    override suspend fun getAllRooms(): Result<List<Room>, DataError.Remote> = safeSuspendTransaction {
        RoomEntity.all().map { it.toRoom() }.throwIfEmpty()
    }

    override suspend fun getRoomDistributionForHousekeeperOn(
        housekeeperId: Int,
        date: LocalDate
    ): Result<List<RoomState>, DataError.Remote> = safeSuspendTransaction {
        HousekeeperShiftRoomTable
            .innerJoin(RoomTable)
            .innerJoin(WorkShiftTable)
            .selectAll()
            .where { (WorkShiftTable.employeeId eq housekeeperId) and (WorkShiftTable.date eq date) }
            .orderBy(RoomTable.roomNumber to SortOrder.DESC)
            .map(::mapShiftRoomsToRoomState)
            .throwIfEmpty()
    }

    override suspend fun getAllRoomStatusOn(date: LocalDate): Result<List<RoomState>, DataError.Remote> =
        safeSuspendTransaction {
            HousekeeperShiftRoomTable
                .innerJoin(RoomTable)
                .innerJoin(WorkShiftTable)
                .selectAll()
                .where { WorkShiftTable.date eq date }
                .map(::mapShiftRoomsToRoomState)
                .throwIfEmpty()
        }
}