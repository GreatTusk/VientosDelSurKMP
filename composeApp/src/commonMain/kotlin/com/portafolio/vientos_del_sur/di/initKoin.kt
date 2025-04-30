package com.portafolio.vientos_del_sur.di

import com.portafolio.vientos_del_sur.di.AppModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

internal fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(AppModule)
    }
}