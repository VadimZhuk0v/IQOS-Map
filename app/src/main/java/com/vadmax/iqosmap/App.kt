package com.vadmax.iqosmap

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.vadmax.iqosmap.di.koin.module.dataModule
import com.vadmax.iqosmap.di.koin.module.repositoryModule
import com.vadmax.iqosmap.di.koin.module.retrofitModule
import com.vadmax.iqosmap.di.koin.module.sharedModule
import com.vadmax.iqosmap.di.koin.module.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this, getString(R.string.google_ads_id))

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            androidFileProperties()
            modules(listOf(sharedModule, retrofitModule, dataModule, repositoryModule, vmModule))
        }

    }

}