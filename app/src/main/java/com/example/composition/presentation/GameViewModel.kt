package com.example.composition.presentation

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.R
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Questions
import com.example.composition.domain.repository.GameRepository
import com.example.composition.domain.usecase.GenerateQuestionUseCase
import com.example.composition.domain.usecase.GetGameSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(

    private val gameRepository: GameRepository,
    @ApplicationContext private val context: Context

) : ViewModel() {

    lateinit var settingsGame: GameSettings
    lateinit var levelGame: Level

//    private val context = application

//    private val repository = GameRepositoryImpl

    val generateQuestionUseCase = GenerateQuestionUseCase(gameRepository)
    val getGameSettingsUseCase = GetGameSettingsUseCase(gameRepository)

    private lateinit var timer: CountDownTimer

    private var _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _question = MutableLiveData<Questions>()
    val question: LiveData<Questions> = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    var percentOfRightAnswers: LiveData<Int> = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    var progressAnswers: LiveData<String> = _progressAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    var enoughCountOfRightAnswers: LiveData<Boolean> = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    var enoughPercentOfRightAnswers: LiveData<Boolean> = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    var minPercent: LiveData<Int> = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    var gameResult: LiveData<GameResult> = _gameResult

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    fun startGame(level: Level) {
        getSettings(level)
        startTime()
        generateQuestion()
        updateProgress()
    }

    private fun getSettings(level: Level) {
        levelGame = level
        settingsGame = getGameSettingsUseCase(level)
        _minPercent.value = settingsGame.minPercentOfRightAnswers
    }

    private fun startTime() {
        timer = object :
            CountDownTimer(
                settingsGame.gameTimeInSecond * MILLIS_IN_SECOND,
                MILLIS_IN_SECOND
            ) {
            override fun onTick(millisUntilFinished: Long) {
                _time.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer.start()
      }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val second = millisUntilFinished / MILLIS_IN_SECOND
        val minutes = second / SECOND_IN_MINUTES
        val leftSecond = second - (minutes * SECOND_IN_MINUTES)
        return String.format("%02d: %02d", minutes, leftSecond)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            _enoughCountOfRightAnswers.value == true && _enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestions,
            settingsGame
        )
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(settingsGame.maxSumValue)
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightNumber
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers.toString(),
            settingsGame.minCountOfRightAnswers.toString()
        )
        _enoughCountOfRightAnswers.value =
            countOfRightAnswers >= settingsGame.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value = percent >= settingsGame.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int {
        if (countOfRightAnswers == 0){
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    companion object {
        const val MILLIS_IN_SECOND = 1000L
        const val SECOND_IN_MINUTES = 60
    }
}