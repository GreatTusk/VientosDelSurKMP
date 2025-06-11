package com.portafolio.vientosdelsur.foryou.screens.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class ForYouViewModel(
    private val employeeRepository: EmployeeRepository,
    userRepository: UserRepository
) : ViewModel() {

    private val _navChannel = Channel<ForYouEvent>()
    val navChannel = _navChannel.receiveAsFlow()

    private val _user = userRepository.currentUser.mapNotNull { user ->
        user?.let { employeeRepository.getEmployee(it.id).takeOrNull() }
    }.onEach { employee ->
        _navChannel.send(ForYouEvent.Navigation(employee.occupation))
    }

    val employee = _user.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5.seconds),
        null
    )
}