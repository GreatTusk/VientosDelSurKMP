package com.portafolio.vientosdelsur.domain.auth

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val currentUser: Flow<User>
}