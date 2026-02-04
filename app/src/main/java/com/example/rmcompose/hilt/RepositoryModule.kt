package com.example.rmcompose.hilt


import com.example.rmcompose.data.remote.repository.RepositoryImpl
import com.example.rmcompose.domain.repository.IRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule() {
    @Binds
    @Singleton
    abstract fun bindRepository(
         impl:RepositoryImpl
    ) : IRepository
}