package com.portafolio.vientosdelsur.foryou.screens.foryou

import androidx.lifecycle.ViewModel
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository

internal class ForYouViewModel(
    userRepository: UserRepository,
    employeeRepository: EmployeeRepository
) : ViewModel() {
    val employee = userRepository.currentEmployee
}