package com.vm.iqosmap

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.vm.iqosmap.di.component.AppComponent
import com.vm.iqosmap.di.component.DaggerAppComponent
import com.vm.iqosmap.di.module.AppModule
import io.fabric.sdk.android.Fabric


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initDagger()
        setUpCrashlytics()

        Crashlytics.getInstance().crash() // Force a crash
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