package com.vadmax.iqosmap

import android.app.Application
import com.vadmax.iqosmap.di.koin.module.dataModule
import com.vadmax.iqosmap.di.koin.module.repositoryModule
import com.vadmax.iqosmap.di.koin.module.retrofitModule
import com.vadmax.iqosmap.di.koin.module.sharedModule
import com.vadmax.iqosmap.di.koin.module.vmModule
import com.vadmax.iqosmap.utils.CrashReportingTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            androidFileProperties()
            modules(sharedModule, retrofitModule, dataModule, repositoryModule, vmModule)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(CrashReportingTree())
    }

}