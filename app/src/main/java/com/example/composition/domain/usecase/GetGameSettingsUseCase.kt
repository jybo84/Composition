package com.example.composition.domain.usecase

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.repository.GameRepository
import javax.inject.Inject

class GetGameSettingsUseCase @Inject constructor(

    private val repository: GameRepository) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}