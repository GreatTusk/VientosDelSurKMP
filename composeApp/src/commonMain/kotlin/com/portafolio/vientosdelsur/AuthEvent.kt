package com.portafolio.vientosdelsur

import com.portafolio.vientosdelsur.domain.auth.User

sealed interface AuthEvent {
    data class OnUserAuthenticated(val user: User) : AuthEvent
    data object OnUserLoggedOut : AuthEvent
    data class OnRegistrationPending(val userId: String) : AuthEvent
}