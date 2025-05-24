package com.portafolio.vientosdelsur.data.auth.mapper

import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.User
import dev.gitlive.firebase.auth.FirebaseUser

fun FirebaseUser.toUser() = User(
    id = uid,
    name = displayName ?: "",
    photoUrl = photoURL,
    email = Email(email!!)
)