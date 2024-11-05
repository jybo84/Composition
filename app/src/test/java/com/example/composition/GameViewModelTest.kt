package com.example.composition

import com.example.composition.domain.repository.GameRepository
import com.example.composition.presentation.GameViewModel
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.Context
import org.mockito.Mockito.mock

class GameViewModelTest {

    private lateinit var subject: GameViewModel
    private val contextMock: Context = mock()
    private val gameRepository: GameRepository = mock()


    @Before
    fun setup() {
        subject = GameViewModel()
    }

    @Test
    fun w_success() {

    }
}


