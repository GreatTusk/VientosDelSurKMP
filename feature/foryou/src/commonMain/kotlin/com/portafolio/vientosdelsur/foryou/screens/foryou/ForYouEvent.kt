package com.portafolio.vientosdelsur.foryou.screens.foryou

import com.portafolio.vientosdelsur.domain.employee.Occupation

sealed interface ForYouEvent {
    data class Navigation(val occupation: Occupation?) : ForYouEvent
}