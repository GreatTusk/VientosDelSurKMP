package com.portafolio.vientosdelsur.data.room.mapper

import com.portafolio.vientosdelsur.domain.room.RoomCleaningStatus
import com.portafolio.vientosdelsur.domain.room.RoomCleaningType
import com.portafolio.vientosdelsur.domain.room.RoomState
import com.portafolio.vientosdelsur.shared.dto.room.RoomStateDto
import com.portafolio.vientosdelsur.shared.dto.room.RoomStatusDto

fun RoomStateDto.toRoomState() = RoomState(
    room = room.toRoom(),
    cleaningType = RoomCleaningType.valueOf(cleaningType),
    cleaningStatus = when (currentStatus.status) {
        RoomStatusDto.PENDING -> RoomCleaningStatus.Pending
        RoomStatusDto.IN_PROGRESS -> RoomCleaningStatus.InCleaning(requireNotNull(currentStatus.updatedAt) { "Room state last update cannot be null" })
        RoomStatusDto.IN_REVISION -> RoomCleaningStatus.InRevision(requireNotNull(currentStatus.updatedAt) { "Room state last update cannot be null" })
        RoomStatusDto.DONE -> RoomCleaningStatus.Done(requireNotNull(currentStatus.updatedAt) { "Room state last update cannot be null" })
    }
)