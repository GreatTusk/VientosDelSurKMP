package com.portafolio.vientosdelsur.foryou.screens.foryou.supervisor

import androidx.lifecycle.ViewModel
import com.f776.core.common.takeOrNull
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import kotlinx.coroutines.flow.flow

internal class SupervisorForYouViewModel(private val employeeRepository: EmployeeRepository): ViewModel() {
    val _employees = flow { emit(employeeRepository.getEmployeesToday().takeOrNull()) }
}