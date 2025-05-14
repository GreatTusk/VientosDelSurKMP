package com.portafolio.vientosdelsur.foryou.screens.housekeeper.util

import kotlinx.datetime.LocalTime

object HourFormatter {
    val hourFormatter = LocalTime.Format {
        hour()
        chars(":")
        minute()
    }
}