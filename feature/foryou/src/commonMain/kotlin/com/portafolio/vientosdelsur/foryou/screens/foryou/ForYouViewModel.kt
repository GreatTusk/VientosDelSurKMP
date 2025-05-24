package com.portafolio.vientosdelsur.foryou.screens.foryou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f776.core.common.Result
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.Employee
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import com.portafolio.vientosdelsur.domain.employee.EmployeeRole
import kotlinx.coroutines.flow.*
import kotlin.time.Duration.Companion.seconds

internal class ForYouViewModel(
    private val employeeRepository: EmployeeRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _user = userRepository.currentUser.map {
        val userName = it.name.split(" ")
        Result.Success(
            Employee(
                id = it.id.toIntOrNull() ?: 2387234,
                firstName = userName[0],
                lastName = userName[2],
                role = EmployeeRole.HOUSEKEEPER
            )
        )
    }

    val user = _user.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(2.seconds),
        Result.Loading
    )
}