package com.vadmax.iqosmap.ui.main

import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.vadmax.iqosmap.base.BaseViewModel
import com.vadmax.iqosmap.utils.MLD
import org.koin.core.inject

class MainViewModel(app: Application) : BaseViewModel<MainRepository>(app) {

    override val repository: MainRepository by inject()

    val ldAdRequest = MLD<AdRequest>(AdRequest.Builder().build())

}