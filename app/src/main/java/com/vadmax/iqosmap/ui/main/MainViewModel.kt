package com.vadmax.iqosmap.ui.main

import android.app.Application
import com.vadmax.iqosmap.base.BaseViewModel
import org.koin.core.inject

class MainViewModel(app: Application) : BaseViewModel<MainRepository>(app) {

    override val repository: MainRepository by inject()

}