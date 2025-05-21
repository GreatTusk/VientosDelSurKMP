package com.portafolio.vientosdelsur.domain.auth.login

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.auth.User

fun interface SignInRepository {
    suspend fun signIn(signInRequest: SignInRequest) : Result<User, DataError>
}