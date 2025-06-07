package com.portafolio.vientosdelsur.data.imageanalysis.util

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import rx.Observable

internal fun <T> Observable<T>.asFlow(): Flow<T> = callbackFlow {
    val subscription = this@asFlow.subscribe(
        { value -> trySend(value).isSuccess },
        { error -> close(error) },
        { close() }
    )

    awaitClose { subscription.unsubscribe() }
}