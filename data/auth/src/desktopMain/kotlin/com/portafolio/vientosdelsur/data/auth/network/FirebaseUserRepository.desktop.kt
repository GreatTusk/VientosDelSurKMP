package com.portafolio.vientosdelsur.data.auth.network

import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal actual class FirebaseUserRepository(
    private val employeeRepository: EmployeeRepository,
    coroutineScope: CoroutineScope
) : UserRepository {

    private val _defaultUser = flow { emit(employeeRepository.getEmployeeByUserId(userId = USER_ID).takeOrNull()) }

    override val currentUser: StateFlow<Employee?> = _defaultUser.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5.seconds),
        initialValue = null
    )

    private companion object {
        private const val USER_ID = "KMgluXQy7Da43RCGIwbqpUNZlNN2"
    }
}