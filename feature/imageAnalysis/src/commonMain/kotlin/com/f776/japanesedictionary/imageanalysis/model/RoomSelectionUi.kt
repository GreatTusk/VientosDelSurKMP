package com.f776.japanesedictionary.imageanalysis.model

import com.portafolio.vientosdelsur.domain.room.Room

internal data class RoomSelectionUi(
    val id: Int,
    val number: String
) {
    val room = requireNotNull(number.first().digitToIntOrNull()) { "Invalid room number" }
}

internal fun Room.toRoomSelectionUi() = RoomSelectionUi(
    id = id,
    number = roomNumber
)