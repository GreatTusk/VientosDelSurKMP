package com.portafolio.vientosdelsur.data.auth.register.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.domain.auth.register.SignUpRepository
import com.portafolio.vientosdelsur.domain.auth.register.SignUpRequest
import dev.gitlive.firebase.auth.FirebaseAuth

internal class FirebaseSignUpRepository(private val firebaseAuth: FirebaseAuth) : SignUpRepository {
    override suspend fun register(signUpRequest: SignUpRequest): Result<User, DataError> {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(signUpRequest.email.email, signUpRequest.password)

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