package com.vadmax.iqosmap.di.module

import android.content.Context
import com.vadmax.iqosmap.data.SharedHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedModule {

    @Provides
    @Singleton
    fun provideSharedHelper(context: Context) = SharedHelper(context)

}