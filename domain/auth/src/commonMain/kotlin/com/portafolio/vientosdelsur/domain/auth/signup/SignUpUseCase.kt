package com.portafolio.vientosdelsur.domain.auth.signup

import com.f776.core.common.*
import com.portafolio.vientosdelsur.domain.auth.AuthService
import com.portafolio.vientosdelsur.domain.auth.Email

class SignUpUseCase(private val authService: AuthService) {
    suspend operator fun invoke(email: String, password: String, confirmPassword: String): EmptyResult<SignUpError> {
        val userMail = try {
            Email(email)
        } catch (e: IllegalArgumentException) {
            return Result.Error(SignUpError.INVALID_EMAIL)
        }

        if (password != confirmPassword) return Result.Error(SignUpError.PASSWORD_MISMATCH)

        authService.register(SignUpRequest(userMail, password))
            .onError {
                return Result.Error(SignUpError.REMOTE)
            }

        return Result.Success(Unit)
    }
}

enum class SignUpError : Error {
    INVALID_EMAIL,
    PASSWORD_MISMATCH,
    REMOTE
}