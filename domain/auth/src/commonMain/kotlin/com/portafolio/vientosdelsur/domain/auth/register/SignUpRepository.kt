package com.portafolio.vientosdelsur.domain.auth.register

import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.auth.User

fun interface SignUpRepository {
    suspend fun register(signUpRequest: SignUpRequest): Result<User, DataError>
}