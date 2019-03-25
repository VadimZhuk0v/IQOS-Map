package com.vadmax.iqosmap.di.koin.module

import com.vadmax.iqosmap.data.DataManager
import com.vadmax.iqosmap.data.SharedHelper
import com.vadmax.iqosmap.data.api.ApiHelper
import com.vadmax.iqosmap.data.api.ApiInterface
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single { provideApi(get()) }
    single { provideApiHelper(get()) }
    single { provideDataManager(get(), get()) }
}

private fun provideDataManager(apiHelper: ApiHelper, sharedHelper: SharedHelper) = DataManager(apiHelper, sharedHelper)

private fun provideApi(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

private fun provideApiHelper(apiInterface: ApiInterface) = ApiHelper(apiInterface)