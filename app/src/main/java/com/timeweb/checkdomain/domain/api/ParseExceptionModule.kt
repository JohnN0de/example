package com.timeweb.checkdomain.domain.api

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface ParseExceptionModule {

    @Binds
    @Singleton
    fun bindParseExceptionUseCase(parseExceptionUseCase: ParseExceptionUseCaseImpl): ParseExceptionUseCase
}