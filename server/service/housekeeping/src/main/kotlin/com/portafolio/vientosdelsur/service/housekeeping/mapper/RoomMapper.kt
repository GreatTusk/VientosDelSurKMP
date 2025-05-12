package com.portafolio.vientosdelsur.service.housekeeping.mapper

import com.portafolio.vientosdelsur.domain.housekeeping.model.Room
import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomCleaningStatus
import com.portafolio.vientosdelsur.domain.housekeeping.model.RoomState
import com.portafolio.vientosdelsur.shared.dto.RoomCleaningStatusDto
import com.portafolio.vientosdelsur.shared.dto.RoomDto
import com.portafolio.vientosdelsur.shared.dto.RoomStateDto
import com.portafolio.vientosdelsur.shared.dto.RoomStatusDto

internal fun Room.toRoomDto() = RoomDto(
    id = id,
    roomNumber = number.toString(),
    roomType = roomType.roomType.name
)

internal fun RoomState.toRoomStateDto() = RoomStateDto(
    room = room.toRoomDto(),
    cleaningType = roomCleaningType.name,
    currentStatus = roomCleaningStatus.toRoomCleaningStatusDto()
)

internal fun RoomCleaningStatus.toRoomCleaningStatusDto(): RoomCleaningStatusDto {
    val (status, updatedAt) = when (this) {
        is RoomCleaningStatus.Done -> RoomStatusDto.DONE to this.changedAt
        is RoomCleaningStatus.InCleaning -> RoomStatusDto.IN_PROGRESS to this.changedAt
        is RoomCleaningStatus.InRevision -> RoomStatusDto.IN_REVISION to this.changedAt
        RoomCleaningStatus.Pending -> RoomStatusDto.PENDING to null
    }
    return RoomCleaningStatusDto(
        status = status,
        updatedAt = updatedAt
    )
}