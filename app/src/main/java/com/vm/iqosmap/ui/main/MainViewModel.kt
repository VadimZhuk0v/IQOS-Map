package com.vm.iqosmap.ui.main

import android.app.Application
import com.vm.iqosmap.App
import com.vm.iqosmap.base.BaseViewModel
import javax.inject.Inject

class MainViewModel(app: Application): BaseViewModel(app) {

    @Inject lateinit var repository: MainRepository

    override fun inject() = App.appComponent.inject(this)

}