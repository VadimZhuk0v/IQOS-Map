package com.vadmax.iqosmap

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.vadmax.iqosmap.di.component.AppComponent
import com.vadmax.iqosmap.di.component.DaggerAppComponent
import com.vadmax.iqosmap.di.module.AppModule
import io.fabric.sdk.android.Fabric


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initDagger()
        setUpCrashlytics()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    private fun setUpCrashlytics() {
        // Set up Crashlytics, disabled for debug builds
        val crashlyticsKit = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
            .build()

        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(this, crashlyticsKit)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }

}