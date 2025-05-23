package com.portafolio.vientosdelsur.data.auth.network

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.auth.AuthService
import com.portafolio.vientosdelsur.domain.auth.signin.SignInRequest
import com.portafolio.vientosdelsur.domain.auth.signup.SignUpRequest
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

internal class FirebaseAuthService(private val firebaseAuth: FirebaseAuth) : AuthService {
    override suspend fun register(signUpRequest: SignUpRequest): EmptyResult<DataError> =
        try {
            firebaseAuth.createUserWithEmailAndPassword(signUpRequest.email.email, signUpRequest.password)
            Result.Success(Unit)
        } catch (_: Exception) {
            currentCoroutineContext().ensureActive()
            Result.Error(DataError.Remote.UNKNOWN)
        }


    override suspend fun signIn(signInRequest: SignInRequest): EmptyResult<DataError> =
        try {
            firebaseAuth.signInWithEmailAndPassword(signInRequest.email.email, signInRequest.password)
            Result.Success(Unit)
        } catch (_: Exception) {
            currentCoroutineContext().ensureActive()
            Result.Error(DataError.Remote.UNKNOWN)
        }
}