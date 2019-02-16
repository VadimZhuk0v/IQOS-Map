package com.vadim.iqosmap.ui.main

import android.app.Application
import com.vadim.iqosmap.App
import com.vadim.iqosmap.base.BaseViewModel
import javax.inject.Inject

class MainViewModel(app: Application): BaseViewModel(app) {

    @Inject lateinit var repository: MainRepository

    override fun inject() = App.appComponent.inject(this)

}