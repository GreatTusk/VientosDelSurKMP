package com.portafolio.vientosdelsur.domain.housekeeping.model

enum class Floor(val number: Int) {
    ONE(1), TWO(2), THREE(3), FOUR(4);

    companion object {
        fun fromRoomNumber(roomNumber: Int): Floor {
            return entries
                .firstOrNull { roomNumber / 100 == it.number }
                ?: error("Invalid room number: $roomNumber")
        }
    }
}