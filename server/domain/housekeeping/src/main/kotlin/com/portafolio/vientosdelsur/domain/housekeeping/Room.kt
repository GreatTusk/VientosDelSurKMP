package com.portafolio.vientosdelsur.domain.housekeeping

data class Room(
    val id: Int,
    val roomNumber: String,
    val cleaningType: CleaningType
) {
    val floor: Floor
        get() = Floor.fromRoomNumber(roomNumber)
}

enum class RoomType {
    SINGLE, DOUBLE, TRIPLE, QUAD
}

sealed class CleaningType(val roomType: RoomType, val workMinutes: Int) {
    class Stay(roomType: RoomType) : CleaningType(
        roomType,
        when (roomType) {
            RoomType.SINGLE -> 18
            RoomType.DOUBLE -> 28
            RoomType.TRIPLE -> 42
            RoomType.QUAD -> 56
        }
    )

    class CheckOut(roomType: RoomType) : CleaningType(
        roomType,
        when (roomType) {
            RoomType.SINGLE -> 25
            RoomType.DOUBLE -> 40
            RoomType.TRIPLE -> 60
            RoomType.QUAD -> 80
        }
    )
}