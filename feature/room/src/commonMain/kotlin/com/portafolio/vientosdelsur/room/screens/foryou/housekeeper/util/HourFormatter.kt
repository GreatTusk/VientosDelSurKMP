package com.portafolio.vientosdelsur.room.screens.foryou.housekeeper.util

import kotlinx.datetime.LocalTime

object HourFormatter {
    val hourFormatter = LocalTime.Format {
        hour()
        chars(":")
        minute()
    }
}