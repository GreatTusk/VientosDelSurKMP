package com.portafolio.vientosdelsur.shared.dto

import kotlinx.serialization.Serializable

@Serializable
data class RoomDto(
    val id: Int,
    val roomNumber: String,
    val roomType: RoomTypeDto
)

@Serializable
data class RoomTypeDto(
    val roomType: String,
    val workUnit: Int,
    val checkOutWorkUnit: Int
)