package com.timeweb.checkdomain.domain.check

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindDomainUseCase(domainUseCaseImpl: DomainUseCaseImpl): DomainUseCase

}