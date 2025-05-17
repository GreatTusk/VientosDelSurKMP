package com.portafolio.vientosdelsur.data.room.mapper

import com.portafolio.vientosdelsur.core.database.entity.room.RoomEntity
import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatus
import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatusTable
import com.portafolio.vientosdelsur.core.database.entity.room.RoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.HousekeeperShiftRoomTable
import com.portafolio.vientosdelsur.core.database.entity.work.RoomCleaningType
import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomCleaningStatus
import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomState
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll

internal fun mapShiftRoomsToRoomState(row: ResultRow): RoomState {
    val roomId = row[RoomTable.id].value
    val room = RoomEntity[roomId].toRoom()

    val latestStatus = RoomStatusTable
        .selectAll()
        .where { RoomStatusTable.roomId eq roomId }
        .orderBy(RoomStatusTable.updatedAt to SortOrder.DESC)
        .firstOrNull()

    return RoomState(
        room = room,
        roomCleaningStatus = latestStatus?.let {
            val status = it[RoomStatusTable.roomStatus]
            val updatedAt = it[RoomStatusTable.updatedAt]

            when (status) {
                RoomStatus.PENDING -> RoomCleaningStatus.Pending
                RoomStatus.IN_CLEANING -> RoomCleaningStatus.InCleaning(updatedAt)
                RoomStatus.IN_REVISION -> RoomCleaningStatus.InRevision(updatedAt)
                RoomStatus.DONE -> RoomCleaningStatus.Done(updatedAt)
            }
        } ?: RoomCleaningStatus.Pending,
        roomCleaningType = when (row[HousekeeperShiftRoomTable.roomCleaningType]) {
            RoomCleaningType.ROOM -> com.portafolio.vientosdelsur.domain.housekeeping.model.RoomCleaningType.ROOM
            RoomCleaningType.GUEST -> com.portafolio.vientosdelsur.domain.housekeeping.model.RoomCleaningType.GUEST
        }
    )
}