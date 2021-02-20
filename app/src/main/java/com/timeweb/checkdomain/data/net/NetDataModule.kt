package com.timeweb.checkdomain.data.net

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.timeweb.checkdomain.BuildConfig
import com.timeweb.checkdomain.domain.common.SchedulersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ApiModule::class, OkHttpProviderModule::class])
@InstallIn(SingletonComponent::class)
class NetDataModule {

    companion object {
        const val BASE_DEFAULT_URL = BuildConfig.BASE_URL
    }

    @Provides
    @Singleton
    fun provideLoggingIntercepter(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }



    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        schedulersProvider: SchedulersProvider
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_DEFAULT_URL)
            .callFactory(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(schedulersProvider.io()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}