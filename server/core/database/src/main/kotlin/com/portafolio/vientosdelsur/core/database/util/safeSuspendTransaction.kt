package com.portafolio.vientosdelsur.core.database.util

import com.f776.core.common.DataError
import com.f776.core.common.Result
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> safeSuspendTransaction(block: Transaction.() -> T): Result<T, DataError.Remote> = try {
    Result.Success(newSuspendedTransaction(Dispatchers.IO, statement = block))
} catch (e: Exception) {
    e.printStackTrace()
    Result.Error(DataError.Remote.UNKNOWN)
}
