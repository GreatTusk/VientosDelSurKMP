package com.portafolio.vientosdelsur.data.auth.network

import com.portafolio.vientosdelsur.data.auth.mapper.toUser
import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class FirebaseUserRepository(
    firebaseAuth: FirebaseAuth,
    private val employeeRepository: EmployeeRepository,
    ioDispatcher: CoroutineDispatcher
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

    override val currentUser: Flow<User?> = firebaseAuth.authStateChanged
        .flowOn(ioDispatcher)
        .map { user ->
            user?.let {
                val isActive = employeeRepository.isUserActive(it.uid)
                it.toUser(isActive)
            }
        }
}