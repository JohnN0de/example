package com.timeweb.checkdomain.data.check

import com.timeweb.checkdomain.domain.check.DomainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainDataModule {

    @Binds
    fun bindDomainRepository(domainRepositoryImpl: DomainRepositoryImpl): DomainRepository

}