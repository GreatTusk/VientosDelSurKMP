package com.portafolio.vientosdelsur.feature.auth.di

import com.portafolio.vientosdelsur.feature.auth.screens.signup.data.ProfilePictureProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal actual val PlatformModule = module {
    single {
        ProfilePictureProvider(
            coroutineScope = get(named("ioCoroutineScope")),
            application = get()
        )
    }
}