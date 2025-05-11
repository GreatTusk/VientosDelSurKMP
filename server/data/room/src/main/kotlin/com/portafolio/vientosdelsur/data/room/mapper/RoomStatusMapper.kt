package com.portafolio.vientosdelsur.data.room.mapper

import com.portafolio.vientosdelsur.core.database.entity.room.RoomStatusEntity
import com.portafolio.vientosdelsur.shared.domain.RoomState

internal fun RoomStatusEntity.toRoomStatus() {
    RoomState(
        room = room.toRoom(),
        roomCleaningType = TODO(),
        roomCleaningStatus = TODO()
    )
}