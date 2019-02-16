package com.vadim.iqosmap

import android.app.Application
import com.vadim.iqosmap.di.component.AppComponent
import com.vadim.iqosmap.di.component.DaggerAppComponent
import com.vadim.iqosmap.di.module.AppModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }

}