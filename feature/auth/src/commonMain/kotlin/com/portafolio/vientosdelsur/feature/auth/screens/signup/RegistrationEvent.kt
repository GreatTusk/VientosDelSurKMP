package com.portafolio.vientosdelsur.feature.auth.screens.signup

sealed interface RegistrationEvent {
    data object OnSuccessfulRegistration : RegistrationEvent
}