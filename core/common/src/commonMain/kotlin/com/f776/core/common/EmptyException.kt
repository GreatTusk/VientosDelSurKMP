package com.f776.core.common

class EmptyException(message: String) : RuntimeException(message)

fun emptyError(message: Any): Nothing = throw EmptyException(message.toString())