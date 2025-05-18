package com.portafolio.vientosdelsur.data.room.mapper

import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatus
import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatusEntity
import com.portafolio.vientosdelsur.domain.room.model.RoomCleaningStatus
import com.portafolio.vientosdelsur.domain.room.model.RoomCleaningType
import com.portafolio.vientosdelsur.domain.room.model.RoomState

internal fun RoomStatusEntity.toRoomStatus(roomCleaningType: RoomCleaningType): RoomState {
    return RoomState(
        room = room.toRoom(),
        roomCleaningType = roomCleaningType,
        roomCleaningStatus = when(roomStatus) {
            RoomStatus.PENDING -> RoomCleaningStatus.Pending
            RoomStatus.IN_CLEANING -> RoomCleaningStatus.InCleaning(updatedAt)
            RoomStatus.IN_REVISION -> RoomCleaningStatus.InRevision(updatedAt)
            RoomStatus.DONE -> RoomCleaningStatus.Done(updatedAt)
        }
    )
}