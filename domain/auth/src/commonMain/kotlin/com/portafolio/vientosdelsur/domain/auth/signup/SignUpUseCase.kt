package com.portafolio.vientosdelsur.domain.auth.signup

import com.f776.core.common.*
import com.portafolio.vientosdelsur.domain.auth.AuthService
import com.portafolio.vientosdelsur.domain.auth.Email
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SignUpUseCase(
    private val authService: AuthService,
    private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(email: String, password: String, confirmPassword: String): EmptyResult<SignUpError> =
        withContext(defaultDispatcher) {
            val userMail = try {
                Email(email)
            } catch (e: IllegalArgumentException) {
                return@withContext Result.Error(SignUpError.INVALID_EMAIL)
            }

            if (password != confirmPassword) return@withContext Result.Error(SignUpError.PASSWORD_MISMATCH)

            authService.register(SignUpRequest(userMail, password))
                .onError {
                    return@withContext Result.Error(SignUpError.REMOTE)
                }

            Result.Success(Unit)
        }
}

enum class SignUpError : Error {
    INVALID_EMAIL,
    PASSWORD_MISMATCH,
    REMOTE
}