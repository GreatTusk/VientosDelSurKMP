package com.portafolio.vientosdelsur.controller.employee.di

import com.portafolio.vientosdelsur.data.employee.di.EmployeeDataModule
import org.koin.dsl.module

val EmployeeControllerModule = module { includes(EmployeeDataModule) }