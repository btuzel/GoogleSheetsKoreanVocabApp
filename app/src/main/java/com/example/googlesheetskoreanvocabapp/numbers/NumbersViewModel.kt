package com.example.googlesheetskoreanvocabapp.numbers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.common.state.AnswerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class NumbersViewModel @Inject constructor(): ViewModel() {

    private val shownNumbers = mutableSetOf<Int>()
    private val incorrectNumbers = mutableListOf<Int>()

    private val _uiState = MutableStateFlow(
        GetNumbers(
            englishNumber = 0,
            defaultNumber = "",
            wasAnswerCorrect = AnswerState.Init,
        )
    )
    val uiState: StateFlow<GetNumbers> = _uiState

    private fun numberToKorean(number: Int): String {
        val units = arrayOf(
            "", "일", "이", "삼", "사", "오", "육", "칠", "팔", "구", "십",
            "십일", "십이", "십삼", "십사", "십오", "십육", "십칠", "십팔", "십구"
        )

        val tens = arrayOf("", "", "이십", "삼십", "사십", "오십", "육십", "칠십", "팔십", "구십")

        val hundreds = arrayOf("", "", "이백", "삼백", "사백", "오백", "육백", "칠백", "팔백", "구백")

        if (number == 0) {
            return "영"
        }

        if (number < 20) {
            return units[number]
        }

        if (number < 100) {
            return tens[number / 10] + if (number % 10 != 0) units[number % 10] else ""
        }

        if (number == 100) {
            return "백"
        }

        if (number == 1000) {
            return "천"
        }

        if (number < 1000) {
            return if (number >= 200) {
                hundreds[(number / 100) % 10] +
                        if (number % 100 == 0)
                            ""
                        else
                            tens[(number % 100) / 10] +
                                    if (number % 10 != 0)
                                        if(getSecondDigit(number) == 1) units[number % 100] else units[number % 10]
                                    else
                                        ""
            } else {
                "백" +
                        hundreds[(number / 100) % 10] +
                        if (number % 100 == 0) ""
                        else tens[(number % 100) / 10] +
                                if (number % 10 != 0) units[number % 10] else ""
            }
        }

        throw IllegalArgumentException("Number out of range")
    }

    private fun getSecondDigit(number: Int): Int {
        return (number % 100) / 10
    }

    fun checkAnswer(englishWord: String, koreanTranslation: String) {
        viewModelScope.launch {
            val correctKoreanTranslation = numberToKorean(englishWord.toInt())
            if (correctKoreanTranslation == koreanTranslation) {
                sendRandomNumber(AnswerState.CorrectAnswer())
            } else {
                shownNumbers.remove(englishWord.toInt())
                incorrectNumbers.add(englishWord.toInt())
                sendRandomNumber(AnswerState.WrongAnswer(correctAnswer = correctKoreanTranslation))
            }
        }
    }

    init {
        viewModelScope.launch {
            sendRandomNumber(AnswerState.Init)
        }
    }

    fun koreanWordChanged(koreanTranslation: String) {
        _uiState.value = _uiState.value.copy(defaultNumber = koreanTranslation)
    }

    fun setStateToInit() {
        _uiState.value = _uiState.value.copy(wasAnswerCorrect = AnswerState.Init)
    }

    private fun sendRandomNumber(wasAnswerCorrect: AnswerState) {
        _uiState.value = GetNumbers(
            getRandomNumberInKorean(),
            "",
            wasAnswerCorrect,
        )
    }

    private fun getRandomNumberInKorean(): Int {
        val randomNumber: Int = if (Random().nextBoolean()) {
            Random().nextInt(101) + 1
        } else {
            Random().nextInt(1001) + 1
        }
        return randomNumber
    }

    data class GetNumbers(
        val englishNumber: Int,
        val defaultNumber: String,
        val wasAnswerCorrect: AnswerState,
    )

}