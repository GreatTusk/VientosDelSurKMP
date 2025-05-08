package com.portafolio.vientosdelsur.domain.room

data class Room(
    val id: Int,
    val roomType: RoomType,
    val number: Int
) {
    val floor get() = Floor.fromRoomNumber(number)
}

enum class RoomType {
    SINGLE, DOUBLE, TRIPLE, QUAD
}