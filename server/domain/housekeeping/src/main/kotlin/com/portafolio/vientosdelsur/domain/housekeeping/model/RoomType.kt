package com.portafolio.vientosdelsur.domain.housekeeping.model

data class RoomTypeDetails(
    val roomType: RoomType,
    val workUnit: Int,
    val checkOutWorkUnit: Int
)

enum class RoomType {
    SINGLE, DOUBLE, TRIPLE, QUAD
}