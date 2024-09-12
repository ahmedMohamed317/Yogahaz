package com.task.yogahaz.di

import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.task.yogahaz.data.remote.AuthApi
import com.task.yogahaz.data.remote.HomeApi
import com.task.yogahaz.data.repository.home.HomeRepositoryImpl
import com.task.yogahaz.data.repository.login.LoginRepositoryImpl
import com.task.yogahaz.data.repository.register.RegisterRepositoryImpl
import com.task.yogahaz.domain.repository.home.HomeRepository
import com.task.yogahaz.domain.repository.login.LoginRepository
import com.task.yogahaz.domain.repository.register.RegisterRepository
import com.task.yogahaz.domain.usecases.login.LoginUseCase
import com.task.yogahaz.domain.usecases.register.RegisterUseCase
import com.task.yogahaz.utils.APIS
import com.task.yogahaz.utils.CONSTANTS
import com.task.yogahaz.utils.UserData
import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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

    @Provides
    @Singleton
    fun provideLoginRepository(api: AuthApi): LoginRepository {
        return LoginRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideRegisterRepository(api: AuthApi): RegisterRepository {
        return RegisterRepositoryImpl(api)
    }
    @Provides
    @Singleton
    fun provideHomeRepository(api: HomeApi): HomeRepository {
        return HomeRepositoryImpl(api)
    }

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