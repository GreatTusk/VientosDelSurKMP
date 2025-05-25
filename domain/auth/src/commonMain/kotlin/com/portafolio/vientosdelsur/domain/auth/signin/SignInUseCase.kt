package com.portafolio.vientosdelsur.domain.auth.signin

import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.f776.core.common.onError
import com.portafolio.vientosdelsur.domain.auth.AuthError
import com.portafolio.vientosdelsur.domain.auth.AuthService
import com.portafolio.vientosdelsur.domain.auth.Email
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SignInUseCase(
    private val authService: AuthService,
    private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(email: String, password: String): EmptyResult<AuthError> =
        withContext(defaultDispatcher) {
            val userMail = try {
                Email(email)
            } catch (e: IllegalArgumentException) {
                return@withContext Result.Error(AuthError.INVALID_EMAIL)
            }

            authService.signIn(SignInRequest(userMail, password))
                .onError {
                    return@withContext Result.Error(AuthError.REMOTE)
                }

            Result.Success(Unit)
        }
}