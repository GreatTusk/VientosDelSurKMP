package com.portafolio.vientosdelsur

import androidx.lifecycle.ViewModel
import com.portafolio.vientosdelsur.domain.auth.Email
import com.portafolio.vientosdelsur.domain.auth.User
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

class AppViewModel(userRepository: UserRepository) : ViewModel() {
    private val _eventChannel = Channel<AuthEvent>()
    val events = _eventChannel.receiveAsFlow()

    val employee = userRepository.currentUser
        .onEach { user ->
            println("Emitted a user! $user")
            when (user) {
                null -> _eventChannel.send(AuthEvent.OnUserLoggedOut)
                else -> {
                    if (user.isEnabled) {
                        _eventChannel.send(
                            AuthEvent.OnUserAuthenticated(
                                User(
                                    id = user.userId,
                                    name = user.fullName,
                                    photoUrl = user.photoUrl,
                                    email = Email(user.email),
                                    isActive = user.isEnabled
                                )
                            )
                        )
                    } else {
                        _eventChannel.send(
                            AuthEvent.OnRegistrationPending(
                                User(
                                    id = user.userId,
                                    name = user.fullName,
                                    photoUrl = user.photoUrl,
                                    email = Email(user.email),
                                    isActive = user.isEnabled
                                )
                            )
                        )
                    }
                }
            }
        }
        .flowOnce()
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