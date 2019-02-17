package com.vm.iqosmap.di.module

import com.vm.iqosmap.data.DataManager
import com.vm.iqosmap.data.SharedHelper
import com.vm.iqosmap.data.api.ApiHelper
import com.vm.iqosmap.data.api.ApiInterface
import dagger.Module
import dagger.Provides
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