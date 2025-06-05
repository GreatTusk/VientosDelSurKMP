package com.portafolio.vientosdelsur.data.user.repository

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult
import com.f776.core.common.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord.CreateRequest
import com.google.firebase.auth.UserRecord.UpdateRequest
import com.portafolio.vientosdelsur.core.firebase.util.await
import com.portafolio.vientosdelsur.domain.user.User
import com.portafolio.vientosdelsur.domain.user.UserRepository

internal class FirebaseUserRepository(private val firebaseAuth: FirebaseAuth) : UserRepository {
    override suspend fun createUser(user: User): EmptyResult<DataError.Remote> {
        val createRequest = CreateRequest()
            .setEmail(user.email)
            .setPhotoUrl(user.photoUrl)

        val future = firebaseAuth.createUserAsync(createRequest).await()
        return Result.Success(Unit)
    }

    override suspend fun updateUser(user: User): EmptyResult<DataError.Remote> {
        val updateRequest = UpdateRequest(user.id)
            .setPhotoUrl(user.photoUrl)
            .setDisplayName(user.name)
            .setDisabled(false)

        val future = firebaseAuth.updateUserAsync(updateRequest).await()

        return Result.Success(Unit)
    }
}