@file:OptIn(ExperimentalStdlibApi::class)

package com.portafolio.vientosdelsur.data.auth.network

import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.data.auth.mapper.toUser
import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class FirebaseUserRepository(
    firebaseAuth: FirebaseAuth,
    private val employeeRepository: EmployeeRepository,
    ioDispatcher: CoroutineDispatcher,
    coroutineScope: CoroutineScope
) : UserRepository {
//    override val currentUser: Flow<User?> = flowOf(
//        User(
//            id = "1",
//            name = "Fer",
//            photoUrl = null,
//            email = Email("email@duoc.cl"),
//            isActive = false
//        )
//    )

    // infinite flow
//    override val currentUser: Flow<User?> = flow<User?> {
//        while (true) {
//            emit(
//                User(
//                    id = "1",
//                    name = "Fer",
//                    photoUrl = null,
//                    email = Email("email@duoc.cl"),
//                    isActive = false
//                )
//            )
//            delay(1000)
//        }
//    }.filterNotNull().onEach { println(it) }
//

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