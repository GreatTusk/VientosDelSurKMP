package com.portafolio.vientosdelsur.data.auth.network

import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal actual class FirebaseUserRepository(
    firebaseAuth: FirebaseAuth,
    private val employeeRepository: EmployeeRepository,
    ioDispatcher: CoroutineDispatcher,
    coroutineScope: CoroutineScope
) : UserRepository {
    override val currentUser: StateFlow<Employee?> = firebaseAuth.authStateChanged
        .flowOn(ioDispatcher)
        .map { user ->
            user?.let { employeeRepository.getEmployeeByUserId(userId = it.uid).takeOrNull() }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5.seconds),
            initialValue = null
        )
}