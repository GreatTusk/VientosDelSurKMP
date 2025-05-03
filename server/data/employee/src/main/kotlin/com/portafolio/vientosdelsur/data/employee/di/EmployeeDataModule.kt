package com.portafolio.vientosdelsur.data.employee.di

import com.portafolio.vientosdelsur.core.data.di.DataModule
import com.portafolio.vientosdelsur.data.employee.repository.DBEmployeeRepository
import com.portafolio.vientosdelsur.data.employee.repository.EmployeeRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val EmployeeDataModule = module {
    includes(DataModule)
    single { DBEmployeeRepository }.bind<EmployeeRepository>()
}