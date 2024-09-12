package com.task.yogahaz.di

import com.task.yogahaz.data.remote.AuthApi
import com.task.yogahaz.data.remote.HomeApi
import com.task.yogahaz.data.repository.home.HomeRepositoryImpl
import com.task.yogahaz.data.repository.login.LoginRepositoryImpl
import com.task.yogahaz.data.repository.register.RegisterRepositoryImpl
import com.task.yogahaz.domain.repository.home.HomeRepository
import com.task.yogahaz.domain.repository.login.LoginRepository
import com.task.yogahaz.domain.repository.register.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {



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
}