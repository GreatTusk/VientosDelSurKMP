package com.portafolio.vientosdelsur.shared.domain

enum class RoomType {
    SINGLE, DOUBLE, TRIPLE, QUAD
}

data class RoomTypeDetails(
    val roomType: RoomType,
    val workUnit: Int,
    val checkOutWorkUnit: Int
)