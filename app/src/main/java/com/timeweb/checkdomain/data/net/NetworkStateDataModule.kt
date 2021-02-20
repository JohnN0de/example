package com.timeweb.checkdomain.data.net

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkStateDataModule {

    @Binds
    @Singleton
    fun bindsNetwork(source: NetworkStateDataSource): NetworkStateSource
}
