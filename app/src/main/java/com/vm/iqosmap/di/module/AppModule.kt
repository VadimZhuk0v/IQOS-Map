package com.vm.iqosmap.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DataModule::class])
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideAppContext(): Context = application

}