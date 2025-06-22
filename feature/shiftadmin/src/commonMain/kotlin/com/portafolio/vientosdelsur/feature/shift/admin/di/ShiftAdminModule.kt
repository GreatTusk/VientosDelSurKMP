package com.portafolio.vientosdelsur.feature.shift.admin.di

import com.portafolio.vientosdelsur.data.shift.di.ShiftDataModule
import org.koin.dsl.module

val ShiftAdminModule = module {
    includes(ShiftDataModule)
}