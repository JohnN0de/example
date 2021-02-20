package com.timeweb.checkdomain.data.net.interceptors

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface InterceptorDataModule {

    @Binds
    @Singleton
    fun bindsNoInternet(source: NoInternetInterceptorRepositoryImpl): NoInternetInterceptorRepository
}