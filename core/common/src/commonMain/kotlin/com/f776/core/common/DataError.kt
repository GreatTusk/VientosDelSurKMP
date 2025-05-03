package com.f776.core.common

sealed interface DataError : Error {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        NOT_FOUND,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : DataError {
        LOGICAL,
        DISK_FULL,
        UNKNOWN
    }
}