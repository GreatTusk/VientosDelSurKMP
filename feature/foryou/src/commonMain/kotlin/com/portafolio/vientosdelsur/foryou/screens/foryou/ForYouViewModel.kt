package com.portafolio.vientosdelsur.foryou.screens.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.DataError
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import com.portafolio.vientosdelsur.domain.employee.Occupation
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class ForYouViewModel(
    private val employeeRepository: EmployeeRepository,
    userRepository: UserRepository
) : ViewModel() {
    private val _user = userRepository.currentUser.mapNotNull { user ->
        user?.let { employeeRepository.getEmployee(it.id) }
    }

    val user = _user.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(2.seconds),
        Result.Loading
    )
}