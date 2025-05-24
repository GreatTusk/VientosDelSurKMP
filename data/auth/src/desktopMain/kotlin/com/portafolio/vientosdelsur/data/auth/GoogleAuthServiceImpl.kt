package com.portafolio.vientosdelsur.data.auth

import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.auth.GoogleAuthError
import com.portafolio.vientosdelsur.domain.auth.GoogleAuthService

internal class GoogleAuthServiceImpl : GoogleAuthService {
    override suspend fun login(): EmptyResult<GoogleAuthError> {
        return Result.Success(Unit)
    }
}