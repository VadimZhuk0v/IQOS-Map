package com.vadmax.iqosmap.di.module

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vadmax.iqosmap.BuildConfig
import com.vadmax.iqosmap.data.SharedHelper
import com.vadmax.iqosmap.utils.network.DataConverterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideHttpCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .connectTimeout(BuildConfig.TIMEOUT_CONNECT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(BuildConfig.TIMEOUT_WRITE.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(BuildConfig.TIMEOUT_READ.toLong(), TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .addConverterFactory(GsonConverterFactory.create(DataConverterFactory.instance))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun authInterceptor(sharedHelper: SharedHelper): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            chain.proceed(requestBuilder.build())
        }
    }
}