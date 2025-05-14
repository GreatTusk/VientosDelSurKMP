package com.portafolio.vientosdelsur.data.employee.di

import com.f776.core.network.di.NetworkModule
import com.portafolio.vientosdelsur.data.employee.repository.EmployeeRepositoryImpl
import com.portafolio.vientosdelsur.domain.employee.EmployeeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val EmployeeDataModule = module {
    includes(NetworkModule)
    singleOf(::EmployeeRepositoryImpl).bind<EmployeeRepository>()
}