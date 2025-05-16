package com.portafolio.vientosdelsur.service.shift.di

import com.portafolio.vientosdelsur.data.employee.di.EmployeeDataModule
import com.portafolio.vientosdelsur.domain.shift.usecase.ScheduleShiftUseCase
import com.portafolio.vientosdelsur.service.shift.ShiftService
import com.portafolio.vientosdelsur.service.shift.ShiftServiceImpl
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val ShiftServiceModule = module {
    includes(EmployeeDataModule)
    factory {
        ScheduleShiftUseCase(
            employeeRepository = get(),
            defaultDispatcher = get<CoroutineDispatcher>(named("defaultDispatcher"))
        )
    }
    singleOf(::ShiftServiceImpl).bind<ShiftService>()
}