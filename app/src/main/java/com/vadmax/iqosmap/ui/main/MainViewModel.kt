package com.vadmax.iqosmap.ui.main

import android.app.Application
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.vadmax.iqosmap.BuildConfig
import com.vadmax.iqosmap.base.BaseViewModel
import com.vadmax.iqosmap.utils.MLD
import org.koin.core.inject

private const val REM_CONF_SERVER_VERSION = "CURRENT_VERSION"

class MainViewModel(app: Application) : BaseViewModel<MainRepository>(app) {

    override val repository: MainRepository by inject()

    init {
        checkForUpdate()
    }

    val ldServerVersion = MLD<Int>()

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