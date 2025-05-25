package com.portafolio.vientosdelsur.domain.auth.oauth

import com.f776.core.common.EmptyResult
import com.f776.core.common.Error

interface GoogleAuthService {
    suspend fun login(): EmptyResult<GoogleAuthError>
}

enum class GoogleAuthError : Error {
    NO_ACCOUNT_ON_DEVICE,
    REMOTE
}