package com.portafolio.vientosdelsur.data.auth.network

import com.portafolio.vientosdelsur.data.auth.mapper.toUser
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull

internal class FirebaseUserRepository(
    private val firebaseAuth: FirebaseAuth,
    private val ioDispatcher: CoroutineDispatcher
) : UserRepository {
    override val currentUser: Flow<User>
        get() = firebaseAuth.authStateChanged.mapNotNull { it?.toUser() }.flowOn(ioDispatcher)
}