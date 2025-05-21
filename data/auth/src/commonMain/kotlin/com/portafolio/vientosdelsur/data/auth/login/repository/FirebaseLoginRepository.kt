package com.portafolio.vientosdelsur.data.auth.login.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.domain.auth.login.LoginRepository
import com.portafolio.vientosdelsur.domain.auth.login.LoginRequest
import dev.gitlive.firebase.auth.FirebaseAuth

internal class FirebaseLoginRepository(private val firebaseAuth: FirebaseAuth) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<User, DataError> {
        val authResult = firebaseAuth.signInWithEmailAndPassword(loginRequest.email.email, loginRequest.password)

        return authResult.user?.let {
            Result.Success(
                User(
                    name = it.displayName ?: "",
                    photoUrl = it.photoURL,
                    email = Email(it.email!!)
                )
            )
        } ?: Result.Error(DataError.Remote.UNKNOWN)
    }
}