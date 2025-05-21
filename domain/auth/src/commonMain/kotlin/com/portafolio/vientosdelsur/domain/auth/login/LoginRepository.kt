package com.portafolio.vientosdelsur.domain.auth.login

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.auth.User

fun interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest) : Result<User, DataError>
}