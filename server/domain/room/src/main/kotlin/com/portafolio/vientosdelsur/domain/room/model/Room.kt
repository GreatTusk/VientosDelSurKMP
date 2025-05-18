package com.portafolio.vientosdelsur.domain.room.model

data class Room(
    val id: Int,
    val roomType: RoomTypeDetails,
    val number: Int
) {
    val floor get() = Floor.fromRoomNumber(number)
}