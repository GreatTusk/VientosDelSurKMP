package com.portafolio.vientosdelsur

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AppViewModel(userRepository: UserRepository) : ViewModel() {
    private val _eventChannel = Channel<AuthEvent>()
    val events = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            userRepository.currentUser.collect { user ->
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
        }
    }
}