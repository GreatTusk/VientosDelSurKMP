package com.portafolio.vientosdelsur

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

class AppViewModel(userRepository: UserRepository) : ViewModel() {
    private val _eventChannel = Channel<AuthEvent>()
    val events = _eventChannel.receiveAsFlow()

    val user = userRepository.currentUser
        .onEach { user ->
            println("Emitted a user! $user")
            when (user) {
                null -> _eventChannel.send(AuthEvent.OnUserLoggedOut)
                else -> {
                    if (user.isActive) {
                        _eventChannel.send(AuthEvent.OnUserAuthenticated(user))
                    } else {
                        _eventChannel.send(AuthEvent.OnRegistrationPending(user))
                    }
                }
            }
        }
        .flowOnce()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2.seconds),
            initialValue = null
        )
}

fun <T> Flow<T>.flowOnce(): Flow<T> {
    var isComplete = false
    return flow {
        if (!isComplete) {
            collect {
                emit(it)
            }
            isComplete = true
        }
    }
}