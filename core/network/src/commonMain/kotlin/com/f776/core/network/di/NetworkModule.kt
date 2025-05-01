package com.f776.core.network.di

import com.f776.core.network.HttpClientFactory
import io.ktor.client.engine.cio.*
import org.koin.dsl.module

val NetworkModule = module {
    single { CIO.create() }
    single { HttpClientFactory.create(get()) }
}