package com.portafolio.vientosdelsur.data.room.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.HousekeeperShiftRoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.WorkShiftTable
import com.portafolio.vientosdelsur.core.database.util.safeSuspendTransaction
import com.portafolio.vientosdelsur.data.room.mapper.mapShiftRoomsToRoomState
import com.portafolio.vientosdelsur.data.room.mapper.toRoom
import com.portafolio.vientosdelsur.domain.housekeeping.RoomRepository
import com.portafolio.vientosdelsur.domain.housekeeping.model.Room
import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomState
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll

internal object DBRoomRepository : RoomRepository {

    override suspend fun getAllRooms(): Result<List<Room>, DataError.Remote> = safeSuspendTransaction {
        RoomEntity.all().map { it.toRoom() }
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
            .map(::mapShiftRoomsToRoomState)
    }

    override suspend fun getAllRoomStatusOn(date: LocalDate): Result<List<RoomState>, DataError.Remote> =
        safeSuspendTransaction {
            HousekeeperShiftRoomTable
                .innerJoin(RoomTable)
                .innerJoin(WorkShiftTable)
                .selectAll()
                .where { WorkShiftTable.date eq date }
                .map(::mapShiftRoomsToRoomState)
        }
}