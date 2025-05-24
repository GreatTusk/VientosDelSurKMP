package com.portafolio.vientosdelsur.feature.auth.screens

sealed interface AuthEvent {
    data object OnUserAuthenticated: AuthEvent
}