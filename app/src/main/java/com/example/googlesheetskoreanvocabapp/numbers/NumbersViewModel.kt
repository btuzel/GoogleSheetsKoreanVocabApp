package com.example.googlesheetskoreanvocabapp.numbers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.common.state.AnswerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class NumbersViewModel @Inject constructor(): ViewModel() {

    var isPureKorean = ""
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

    private fun numberToKoreanChinese(number: Int): String {
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

    private fun numberToKoreanPureKorean(number: Int): String {
        val units = arrayOf(
            "", "하나", "둘", "셋", "넷", "다섯", "여섯", "일곱", "여덟", "아홉", "열",
            "열하나", "열둘", "열셋" ,"열넷", "열다섯", "열여섯", "열일곱", "열여덟", "열아홉"
        )

        val tens = arrayOf("", "", "스물", "서른", "마흔", "쉰", "예순", "일흔", "여든", "아흔")


        if (number < 20) {
            return units[number]
        }

        if (number < 100) {
            return tens[number / 10] + if (number % 10 != 0) units[number % 10] else ""
        }

        throw IllegalArgumentException("Number out of range")
    }

    private fun getSecondDigit(number: Int): Int {
        return (number % 100) / 10
    }

    fun checkAnswer(englishWord: String, koreanTranslation: String, isPureKorean : Boolean) {
        viewModelScope.launch {
            val correctKoreanTranslation = if (isPureKorean) {
                numberToKoreanPureKorean(englishWord.toInt())
            } else {
                numberToKoreanChinese(englishWord.toInt())
            }
            val isCorrect = correctKoreanTranslation == koreanTranslation
            if (isCorrect) {
                sendRandomNumber(AnswerState.CorrectAnswer())
            } else {
                val number = englishWord.toInt()
                shownNumbers.remove(number)
                incorrectNumbers.add(number)
                sendRandomNumber(AnswerState.WrongAnswer(correctAnswer = correctKoreanTranslation))
            }
        }
    }

    init {
        viewModelScope.launch {
            delay(500L)
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
            getRandomNumberInKorean(isPureKorean=="true"),
            "",
            wasAnswerCorrect,
        )
    }

    private fun getRandomNumberInKorean(isPureKorean: Boolean): Int {
        if(isPureKorean) {
            return Random().nextInt(99) + 1
        }
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