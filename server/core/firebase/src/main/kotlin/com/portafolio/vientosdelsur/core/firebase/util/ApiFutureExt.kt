package com.portafolio.vientosdelsur.core.firebase.util

import com.google.api.core.ApiFuture
import com.google.api.core.ApiFutureToListenableFuture
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.guava.asDeferred

/** @return Current [ApiFuture] as a [Deferred]. */
private fun <T> ApiFuture<T>.asDeferred(): Deferred<T> =
    ApiFutureToListenableFuture(this).asDeferred()

/** @return Awaited value from the [ApiFuture]. */
suspend fun <T> ApiFuture<T>.await(): T = asDeferred().await()