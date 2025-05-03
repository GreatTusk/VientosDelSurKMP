package com.portafolio.vientosdelsur

import android.app.Application
import com.portafolio.vientosdelsur.di.initKoin
import org.koin.android.ext.koin.androidContext

class VientosDelSurApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@VientosDelSurApplication)
//            workManagerFactory()
        }
    }
}