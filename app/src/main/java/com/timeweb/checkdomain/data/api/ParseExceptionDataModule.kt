package com.timeweb.checkdomain.data.api

import com.timeweb.checkdomain.domain.api.ParseExceptionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ParseExceptionDataModule {

    @Singleton
    @Binds
    fun bindsParseException(source: ParseExceptionRepositoryImpl): ParseExceptionRepository
}
