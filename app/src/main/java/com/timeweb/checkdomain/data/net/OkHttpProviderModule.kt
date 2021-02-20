package com.timeweb.checkdomain.data.net

import com.timeweb.checkdomain.data.net.interceptors.InterceptorDataModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [InterceptorDataModule::class])
@InstallIn(SingletonComponent::class)
class OkHttpProviderModule {

    companion object {
        const val TIMEOUT_IN_SEC: Long = 60
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_IN_SEC, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}