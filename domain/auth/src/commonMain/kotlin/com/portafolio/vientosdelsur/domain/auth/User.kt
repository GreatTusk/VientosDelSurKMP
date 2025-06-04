package com.portafolio.vientosdelsur.domain.auth

data class User(
    val id: String,
    val name: String,
    val photoUrl: String?,
    val email: Email,
    val isActive: Boolean
)

fun String.getFirstAndLastName(): Pair<String, String> {
    val words = split(" ")

    if (words.isEmpty()) return "" to ""
    if (words.size == 1) return words[0] to ""

    val middle = words.size / 2
    val firstName = words.subList(0, middle)
    val lastName = words.subList(middle, words.size)

    return firstName.joinToString(" ") to lastName.joinToString(" ")
}