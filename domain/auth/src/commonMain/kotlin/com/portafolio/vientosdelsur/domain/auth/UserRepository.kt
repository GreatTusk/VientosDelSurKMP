package com.portafolio.vientosdelsur.domain.auth

import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val currentUser: StateFlow<User?>
}