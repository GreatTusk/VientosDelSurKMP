package com.portafolio.vientosdelsur.service.employee.di

import com.portafolio.vientosdelsur.data.employee.di.EmployeeDataModule
import com.portafolio.vientosdelsur.service.employee.EmployeeService
import com.portafolio.vientosdelsur.service.employee.EmployeeServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val EmployeeServiceModule = module {
    includes(EmployeeDataModule)
    singleOf(::EmployeeServiceImpl).bind<EmployeeService>()
}