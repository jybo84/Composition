package com.example.composition.di

import com.example.composition.domain.repository.GameRepository
import com.example.composition.domain.usecase.GenerateQuestionUseCase
import com.example.composition.domain.usecase.GetGameSettingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideGenQuestionUseCase(repository: GameRepository): GenerateQuestionUseCase{
        return GenerateQuestionUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideGetGameSettingsUseCase(repository: GameRepository): GetGameSettingsUseCase {
      return GetGameSettingsUseCase(repository)
    }
}