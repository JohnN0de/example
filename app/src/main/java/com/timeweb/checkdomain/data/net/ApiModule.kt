package com.timeweb.checkdomain.data.net

import com.timeweb.checkdomain.data.api.ApiDomain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideApiDomain(retrofit: Retrofit): ApiDomain {
        return retrofit.create(ApiDomain::class.java)
    }

}