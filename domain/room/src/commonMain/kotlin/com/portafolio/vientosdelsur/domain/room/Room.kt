package com.portafolio.vientosdelsur.domain.room

data class Room(
    val id: Int,
    val roomNumber: String,
    val roomType: RoomType
)

enum class RoomType {
    SINGLE, DOUBLE, TRIPLE, QUAD
}