package com.timeweb.checkdomain.domain

import com.timeweb.checkdomain.domain.api.ParseExceptionModule
import com.timeweb.checkdomain.domain.common.CommonModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        CommonModule::class,
        ParseExceptionModule::class
    ]
)
interface DomainModule {

}