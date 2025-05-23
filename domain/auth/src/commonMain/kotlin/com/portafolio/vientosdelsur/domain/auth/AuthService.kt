package com.portafolio.vientosdelsur.domain.auth

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.portafolio.vientosdelsur.domain.auth.signin.SignInRequest
import com.portafolio.vientosdelsur.domain.auth.signup.SignUpRequest

interface AuthService {
    suspend fun register(signUpRequest: SignUpRequest): EmptyResult<DataError>
    suspend fun signIn(signInRequest: SignInRequest) : EmptyResult<DataError>
}