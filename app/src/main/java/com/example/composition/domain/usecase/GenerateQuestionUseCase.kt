package com.example.composition.domain.usecase

import com.example.composition.domain.entity.Questions
import com.example.composition.domain.repository.GameRepository
import javax.inject.Inject

class GenerateQuestionUseCase @Inject constructor(

    private val repository: GameRepository
) {
    operator fun invoke(maxSumValue: Int): Questions {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTION)
    }

    private companion object {
        const val COUNT_OF_OPTION = 6
    }
}