package com.portafolio.vientosdelsur.data.auth.di

import com.portafolio.vientosdelsur.data.auth.network.FirebaseUserRepository
import com.portafolio.vientosdelsur.domain.auth.UserRepository
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val PlatformModule = module {
    single {
        FirebaseUserRepository(
            firebaseAuth = get(),
            employeeRepository = get(),
            ioDispatcher = get(named("ioDispatcher")),
            coroutineScope = get(named("defaultCoroutineScope"))
        )
    }.bind<UserRepository>()
}