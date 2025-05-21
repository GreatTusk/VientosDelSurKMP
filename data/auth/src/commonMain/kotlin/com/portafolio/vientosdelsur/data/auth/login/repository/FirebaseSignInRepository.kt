package com.portafolio.vientosdelsur.data.auth.login.repository

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.domain.auth.login.SignInRepository
import com.portafolio.vientosdelsur.domain.auth.login.SignInRequest
import dev.gitlive.firebase.auth.FirebaseAuth

internal class FirebaseSignInRepository(private val firebaseAuth: FirebaseAuth) : SignInRepository {
    override suspend fun signIn(signInRequest: SignInRequest): Result<User, DataError> {
        val authResult = firebaseAuth.signInWithEmailAndPassword(signInRequest.email.email, signInRequest.password)

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