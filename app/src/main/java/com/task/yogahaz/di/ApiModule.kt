package com.task.yogahaz.di


import com.task.yogahaz.data.remote.AuthApi
import com.task.yogahaz.data.remote.HomeApi
import com.task.yogahaz.utils.constants.APIS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideAuthApi(client: OkHttpClient): AuthApi {
        return Retrofit.Builder()
            .baseUrl(APIS.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(AuthApi::class.java)
    }
    @Provides
    @Singleton
    fun provideHomeApi(client: OkHttpClient): HomeApi {
        return Retrofit.Builder()
            .baseUrl(APIS.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(HomeApi::class.java)
    }
}