package com.portafolio.vientosdelsur.controller.employee.di

import com.portafolio.vientosdelsur.service.employee.di.EmployeeServiceModule
import org.koin.dsl.module

val EmployeeControllerModule = module { includes(EmployeeServiceModule) }