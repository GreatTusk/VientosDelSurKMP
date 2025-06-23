package com.portafolio.vientosdelsur.foryou.screens.foryou.supervisor.ui

import com.portafolio.vientosdelsur.domain.employee.Occupation

internal val Occupation.displayName: String
    get() = when (this) {
        Occupation.HOUSEKEEPER -> "Mucama"
        Occupation.SUPERVISOR -> "Supervisora"
        Occupation.ADMIN -> "Administrador"
        Occupation.COOK -> "Cocinero"
    }
