package com.vadmax.iqosmap.ui.main

import android.app.Application
import com.vadmax.iqosmap.App
import com.vadmax.iqosmap.base.BaseViewModel
import javax.inject.Inject

class MainViewModel(app: Application): BaseViewModel(app) {

    @Inject lateinit var repository: MainRepository

    override fun inject() = App.appComponent.inject(this)

}