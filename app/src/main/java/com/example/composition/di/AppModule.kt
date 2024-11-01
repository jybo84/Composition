package com.example.composition.di

import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Singleton
    @Binds
  fun bindGameRepository(impl: GameRepositoryImpl): GameRepository
}