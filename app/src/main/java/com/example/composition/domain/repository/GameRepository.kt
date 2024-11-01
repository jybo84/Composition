package com.example.composition.domain.repository

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Questions

//@Component(modules = [GameRepositoryImpl::class])
interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Questions

    fun getGameSettings(level: Level): GameSettings
}