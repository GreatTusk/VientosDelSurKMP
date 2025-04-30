package com.portafolio.vientos_del_sur

import android.app.Application
import com.portafolio.vientos_del_sur.di.initKoin
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