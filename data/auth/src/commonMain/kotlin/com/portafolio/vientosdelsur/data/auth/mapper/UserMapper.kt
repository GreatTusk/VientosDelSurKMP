package com.portafolio.vientosdelsur.data.auth.mapper

import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.User
import dev.gitlive.firebase.auth.FirebaseUser

internal fun FirebaseUser.toUser(isActive: Boolean) = User(
    id = uid,
    name = displayName ?: "",
    photoUrl = photoURL,
    email = Email(email!!),
    isActive = isActive
)