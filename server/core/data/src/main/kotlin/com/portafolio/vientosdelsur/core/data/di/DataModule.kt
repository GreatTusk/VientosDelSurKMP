package com.portafolio.vientosdelsur.core.data.di

import com.portafolio.vientosdelsur.core.data.DatabaseFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val DataModule = module { single(named("database_factory")) { DatabaseFactory.connectToDb } }