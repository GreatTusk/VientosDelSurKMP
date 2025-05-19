package com.f776.core.common

sealed interface LoadingState<out T> {
    data object Loading : LoadingState<Nothing>
    data object Empty : LoadingState<Nothing>
    data class Success<T>(val data: T) : LoadingState<T>
    data class Error(val error: com.f776.core.common.Error) : LoadingState<Nothing>
}