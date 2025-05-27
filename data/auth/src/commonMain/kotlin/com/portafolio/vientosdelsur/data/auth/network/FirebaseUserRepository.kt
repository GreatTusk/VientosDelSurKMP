package com.portafolio.vientosdelsur.data.auth.network

import com.portafolio.vientosdelsur.data.auth.mapper.toUser
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class FirebaseUserRepository(
    private val firebaseAuth: FirebaseAuth,
    private val employeeRepository: EmployeeRepository,
    private val ioDispatcher: CoroutineDispatcher
) : UserRepository {
    override val currentUser: Flow<User?>
        get() = firebaseAuth.authStateChanged
            .flowOn(ioDispatcher)
            .map { user ->
                user?.let {
                    val isActive = employeeRepository.isUserActive(it.uid)
                    it.toUser(isActive)
                }
            }
}