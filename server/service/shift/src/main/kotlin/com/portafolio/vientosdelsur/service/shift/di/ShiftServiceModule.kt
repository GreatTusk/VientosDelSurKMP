package com.portafolio.vientosdelsur.service.shift.di

import com.portafolio.vientosdelsur.data.employee.di.EmployeeDataModule
import com.portafolio.vientosdelsur.data.shift.di.ShiftDataModule
import com.portafolio.vientosdelsur.domain.shift.usecase.ScheduleShiftUseCase
import com.portafolio.vientosdelsur.service.shift.ShiftSchedulerService
import com.portafolio.vientosdelsur.service.shift.ShiftSchedulerServiceImpl
import com.portafolio.vientosdelsur.service.shift.ShiftService
import com.portafolio.vientosdelsur.service.shift.ShiftServiceImpl
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val ShiftSchedulerServiceModule = module {
    includes(EmployeeDataModule, ShiftDataModule)
    factory {
        ScheduleShiftUseCase(
            employeeRepository = get(),
            defaultDispatcher = get<CoroutineDispatcher>(named("defaultDispatcher"))
        )
    }
    singleOf(::ShiftSchedulerServiceImpl).bind<ShiftSchedulerService>()
    singleOf(::ShiftServiceImpl).bind<ShiftService>()
}