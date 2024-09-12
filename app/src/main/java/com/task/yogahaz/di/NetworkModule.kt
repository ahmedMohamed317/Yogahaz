package com.task.yogahaz.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.task.yogahaz.utils.constants.CONSTANTS
import com.task.yogahaz.utils.constants.UserData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideHttpClient(app: Application): OkHttpClient {

        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(CONSTANTS.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CONSTANTS.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ChuckerInterceptor(app))
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header(
                        UserData.AUTHORIZATION,
                        UserData.BEARER + UserData.TOKEN
                    )
                    .build()
                chain.proceed(request)
            })
        return okHttpClientBuilder.build()
    }
}