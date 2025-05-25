package com.portafolio.vientosdelsur.domain.auth

import kotlin.jvm.JvmInline

@JvmInline
value class Email(val email: String) {
    companion object {
        private val emailAddressRegex = Regex(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
    }

    init {
        require(emailAddressRegex.matches(email)) { "Invalid email format" }
    }
}