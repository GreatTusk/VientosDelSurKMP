package com.portafolio.vientosdelsur.domain.auth

import com.portafolio.vientosdelsur.domain.employee.Employee
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val currentUser: StateFlow<User?>
    val currentEmployee: StateFlow<Employee?>
}