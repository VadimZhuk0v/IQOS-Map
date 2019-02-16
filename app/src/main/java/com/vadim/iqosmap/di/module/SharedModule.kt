package com.vadim.iqosmap.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import com.vadim.iqosmap.data.SharedHelper
import javax.inject.Singleton

@Module
class SharedModule {

    @Provides
    @Singleton
    fun provideSharedHelper(context: Context) = SharedHelper(context)

}