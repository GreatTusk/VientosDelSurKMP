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
            when (user) {
                null -> _eventChannel.send(AuthEvent.OnUserLoggedOut)
                else -> _eventChannel.send(AuthEvent.OnUserAuthenticated(user))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2.seconds),
            initialValue = null
        )

}