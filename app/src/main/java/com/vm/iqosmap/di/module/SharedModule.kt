package com.vm.iqosmap.di.module

import android.content.Context
import com.vm.iqosmap.data.SharedHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedModule {

    @Provides
    @Singleton
    fun provideSharedHelper(context: Context) = SharedHelper(context)

}