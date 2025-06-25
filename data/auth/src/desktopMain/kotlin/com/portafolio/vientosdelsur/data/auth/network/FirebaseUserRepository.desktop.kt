package com.portafolio.vientosdelsur.data.auth.network

import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.data.auth.BuildConfig
import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal actual class FirebaseUserRepository(
    private val employeeRepository: EmployeeRepository,
    coroutineScope: CoroutineScope
) : UserRepository {

    private val _defaultEmployee = flow { emit(employeeRepository.getEmployeeByUserId(userId = USER_ID).takeOrNull()) }
    private val _defaultUser = flow {
        emit(
            User(
                id = USER_ID,
                name = "",
                photoUrl = "",
                email = Email("fern.belmar@duocuc.cl"),
                isActive = true
            )
        )
    }

    override val currentUser: StateFlow<User?> = _defaultUser.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5.seconds),
        initialValue = null
    )

    override val currentEmployee = _defaultEmployee.stateIn(
        coroutineScope,
        SharingStarted.WhileSubscribed(5.seconds),
        null
    )

    private companion object {
        private const val SUPERVISOR_USER_ID = "2oJjFDXkLlQxKoZ4rB7gQ3avAnJ2"
        private const val ADMIN_USER_ID = "4vAiM2MbIDQeTt82KOEsXkI7J1v1"
        private const val HOUSEKEEPER_USER_ID = "KMgluXQy7Da43RCGIwbqpUNZlNN2"

        private val USER_ID = when (BuildConfig.DEMO_ROLE) {
            "admin" -> ADMIN_USER_ID
            "housekeeper" -> HOUSEKEEPER_USER_ID
            "supervisor" -> SUPERVISOR_USER_ID
            else -> error("Role not provided!")
        }
    }
}