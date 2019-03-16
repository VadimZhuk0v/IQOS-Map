package com.vadmax.iqosmap.ui.main

import android.app.Application
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.vadmax.iqosmap.App
import com.vadmax.iqosmap.BuildConfig
import com.vadmax.iqosmap.base.BaseViewModel
import com.vadmax.iqosmap.utils.MLD

private const val REM_CONF_SERVER_VERSION = "CURRENT_VERSION"

class MainViewModel(app: Application) : BaseViewModel<MainRepository>(app) {

    init {
        checkForUpdate()
    }

    val ldServerVersion = MLD<Int>()

    override fun inject() = App.appComponent.inject(this)

    private fun checkForUpdate() {
        val configs = FirebaseRemoteConfig.getInstance()
        configs.fetch().addOnSuccessListener {
            try {
                configs.activateFetched()
                val serverVersion = configs.getString(REM_CONF_SERVER_VERSION).toInt()
                if (BuildConfig.VERSION_CODE < serverVersion)
                    ldServerVersion.postValue(serverVersion)
            } catch (e: Exception) {

            }

        }
    }

}