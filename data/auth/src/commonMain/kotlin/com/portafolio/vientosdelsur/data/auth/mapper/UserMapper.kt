package com.portafolio.vientosdelsur.data.auth.mapper

import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.User
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.UserInfo

fun FirebaseUser.toUser() = User(
    name = displayName ?: "",
    photoUrl = photoURL,
    email = Email(email!!)
)