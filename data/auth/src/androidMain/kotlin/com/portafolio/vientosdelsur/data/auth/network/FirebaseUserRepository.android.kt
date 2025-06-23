@file:OptIn(ExperimentalCoroutinesApi::class)

package com.portafolio.vientosdelsur.data.auth.network

import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.data.auth.mapper.toUser
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal actual class FirebaseUserRepository(
    firebaseAuth: FirebaseAuth,
    private val employeeRepository: EmployeeRepository,
    ioDispatcher: CoroutineDispatcher,
    coroutineScope: CoroutineScope
) : UserRepository {
    override val currentUser: StateFlow<User?> = firebaseAuth.authStateChanged
        .flowOn(ioDispatcher)
        .map { user ->
            user?.let {
                val isActive = employeeRepository.isUserActive(it.uid)
                it.toUser(isActive)
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = null
        )

    override val currentEmployee = currentUser.flatMapLatest { user ->
        flow {
            user?.id?.let {
                emit(employeeRepository.getEmployeeByUserId(userId = it).takeOrNull())
            }
        }
    }.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(5.seconds),
        null
    )
}