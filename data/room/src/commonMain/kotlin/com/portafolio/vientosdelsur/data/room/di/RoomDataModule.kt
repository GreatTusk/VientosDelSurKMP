package com.portafolio.vientosdelsur.data.room.di

import com.f776.core.network.di.NetworkModule
import com.portafolio.vientosdelsur.data.room.network.KtorRemoteRoomDatasource
import com.portafolio.vientosdelsur.data.room.network.RemoteRoomDatasource
import com.portafolio.vientosdelsur.data.room.repository.RoomRepositoryImpl
import com.portafolio.vientosdelsur.domain.room.RoomRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val RoomDataModule = module {
    includes(NetworkModule)

    singleOf(::KtorRemoteRoomDatasource).bind<RemoteRoomDatasource>()
    singleOf(::RoomRepositoryImpl).bind<RoomRepository>()
}