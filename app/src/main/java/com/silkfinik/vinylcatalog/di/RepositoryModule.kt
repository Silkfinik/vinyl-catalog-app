package com.silkfinik.vinylcatalog.di

import com.silkfinik.vinylcatalog.data.repository.LocalVinylRepositoryImpl
import com.silkfinik.vinylcatalog.domain.repository.VinylRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindVinylRepository(
        localVinylRepositoryImpl: LocalVinylRepositoryImpl
    ): VinylRepository
}
