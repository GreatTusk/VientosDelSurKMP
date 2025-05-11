package com.portafolio.vientosdelsur.core.database.entity.work

import kotlinx.datetime.LocalTime

enum class Shift(val startTime: LocalTime, val endTime: LocalTime) {
    GENERAL_DUTY(LocalTime(9, 0, 0), LocalTime(17, 0, 0)),
    KITCHEN_ASSISTANT(LocalTime(8, 0, 0), LocalTime(16, 0, 0)),
    KITCHEN_LEAD(LocalTime(7, 30, 0), LocalTime(15, 30, 0))
}