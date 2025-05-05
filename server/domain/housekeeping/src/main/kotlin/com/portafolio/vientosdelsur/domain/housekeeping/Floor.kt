package com.portafolio.vientosdelsur.domain.housekeeping

enum class Floor(val number: Int) {
    ONE(1), TWO(2), THREE(3), FOUR(4);

    companion object {
        fun fromRoomNumber(roomNumber: String): Floor {
            return entries
                .firstOrNull { roomNumber.startsWith(it.number.toString()) }
                ?: error("Invalid room number: $roomNumber")
        }
    }
}