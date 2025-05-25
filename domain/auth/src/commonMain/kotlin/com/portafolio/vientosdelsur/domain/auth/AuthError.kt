package com.portafolio.vientosdelsur.domain.auth

import com.f776.core.common.Error

enum class AuthError : Error {
    INVALID_EMAIL,
    PASSWORD_MISMATCH,
    REMOTE
}