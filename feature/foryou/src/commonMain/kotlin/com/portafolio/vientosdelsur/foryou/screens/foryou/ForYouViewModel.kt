package com.portafolio.vientosdelsur.foryou.screens.foryou

import androidx.lifecycle.ViewModel
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

internal class ForYouViewModel(
    private val employeeRepository: EmployeeRepository,
    userRepository: UserRepository
) : ViewModel() {

    private val _navChannel = Channel<ForYouEvent>()
    val navChannel = _navChannel.receiveAsFlow()

    val employee = userRepository.currentUser.mapNotNull { user ->
        user?.let { employeeRepository.getEmployeeByUserId(it.id).takeOrNull() }
    }.onEach { employee ->
        _navChannel.send(ForYouEvent.Navigation(employee.occupation))
    }
}