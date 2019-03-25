package com.vadmax.iqosmap.di.koin.module

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.vadmax.iqosmap.BuildConfig
import com.vadmax.iqosmap.data.SharedHelper
import com.vadmax.iqosmap.utils.network.DataConverterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    single { provideHttpCache(androidContext()) }
    single { provideGson() }
    single { provideLoggingInterceptor() }
    single { authInterceptor(get()) }
    single { provideOkHttpClient(cache = get(), interceptor = get(), loggingInterceptor = get()) }
    factory { provideRetrofit(gson = get(), okHttpClient = get()) }
}


fun provideHttpCache(context: Context): Cache {
    val cacheSize = 10 * 1024 * 1024
    return Cache(context.cacheDir, cacheSize.toLong())
}

fun provideGson(): Gson {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    return gsonBuilder.create()
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(
        if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    )
}

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

fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .addConverterFactory(GsonConverterFactory.create(DataConverterFactory.instance))
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()
}

fun authInterceptor(sharedHelper: SharedHelper): Interceptor {
    return Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        chain.proceed(requestBuilder.build())
    }
}