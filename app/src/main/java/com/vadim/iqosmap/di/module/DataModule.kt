package com.vadim.iqosmap.di.module

import com.vadim.iqosmap.data.DataManager
import com.vadim.iqosmap.data.SharedHelper
import com.vadim.iqosmap.data.api.ApiHelper
import dagger.Module
import dagger.Provides
import com.vadim.iqosmap.data.api.ApiInterface
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class, SharedModule::class])
class DataModule {

    @Provides
    @Singleton
    fun provideDataManager(apiHelper: ApiHelper, sharedHelper: SharedHelper) = DataManager(apiHelper, sharedHelper)


    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)


    @Provides
    @Singleton
    fun provideApiHelper(apiInterface: ApiInterface) = ApiHelper(apiInterface)

}