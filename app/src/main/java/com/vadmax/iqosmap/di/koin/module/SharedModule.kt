package com.vadmax.iqosmap.di.koin.module

import com.vadmax.iqosmap.data.SharedHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedModule = module {
    single { SharedHelper(androidContext()) }
}