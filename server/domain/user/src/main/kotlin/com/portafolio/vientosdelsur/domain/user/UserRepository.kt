package com.portafolio.vientosdelsur.domain.user

import com.f776.core.common.DataError
import com.f776.core.common.EmptyResult

interface UserRepository {
    suspend fun createUser(user: User): EmptyResult<DataError.Remote>
    suspend fun updateUser(user: User): EmptyResult<DataError.Remote>
}