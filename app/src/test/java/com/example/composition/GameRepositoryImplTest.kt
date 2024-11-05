package com.example.composition

import com.example.composition.data.GameRepositoryImpl
import org.junit.Before
import org.junit.Test

class GameRepositoryImplTest {

    private lateinit var subject: GameRepositoryImpl


    @Before
    fun setup(){
        subject = GameRepositoryImpl()
    }

    @Test
    fun generateQuestion_success(){

        subject.generateQuestion()
    }
}



