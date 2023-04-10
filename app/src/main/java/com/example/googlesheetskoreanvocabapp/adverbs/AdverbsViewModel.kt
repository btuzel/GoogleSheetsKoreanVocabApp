package com.example.googlesheetskoreanvocabapp.adverbs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdverbsViewModel @Inject constructor(
    private val getAdverbs: GetAdverbs,
    private val addAdverbs: AddAdverbs,
    private val deleteAdverb: DeleteAdverb
) : ViewModel() {

    private val initialUiState = QuizUiState.GetWords(
        englishWord = "",
        defaultWord = "",
        wasAnswerCorrect = AnswerState.Init
    )
    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<QuizUiState> = _uiState
    private lateinit var listOfAdverbs: Pair<List<String>, List<String>>

    init {
        viewModelScope.launch {
            fixAllWords()
            sendRandomEnglishWord(AnswerState.Init)
        }
    }

    private val shownWords = mutableSetOf<String>()

    fun addAdverbsToColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            addAdverbs(englishWord, koreanWord)
        }
    }

    fun deleteAdverbFromColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            deleteAdverb(englishWord, koreanWord)
        }
    }

    private fun getRandomEnglishWord(): String {
        val randomWord = listOfAdverbs.first.random()
        shownWords.add(randomWord)
        return randomWord
    }

    private suspend fun fixAllWords() {
        listOfAdverbs = getAdverbs()
            .let {
                Pair(
                    it.first.map { it.replace("[", "").replace("]", "") },
                    it.second.map { it.replace("[", "").replace("]", "") })
            }
    }

    private fun sendRandomEnglishWord(wasAnswerCorrect: AnswerState) {
        _uiState.value = QuizUiState.GetWords(getRandomEnglishWord(), "", wasAnswerCorrect)
    }

    fun koreanWordChanged(koreanTranslation: String) {
        _uiState.value = _uiState.value.copy(defaultWord = koreanTranslation)
    }

    fun setStateToInit() {
        _uiState.value = _uiState.value.copy(wasAnswerCorrect = AnswerState.Init)
    }


    fun checkAnswer(englishWord: String, koreanTranslation: String) {
        viewModelScope.launch {
            val correctKoreanTranslation =
                listOfAdverbs.first.zip(listOfAdverbs.second)
                    .find { it.first == englishWord.split("[")[0] }!!.second
            if (shownWords.size == listOfAdverbs.first.size) {
                _uiState.value = QuizUiState.GetWords("", "", AnswerState.Finished)
            } else {
                if (correctKoreanTranslation == koreanTranslation) {
                    sendRandomEnglishWord(AnswerState.CorrectAnswer())
                    shownWords.remove(englishWord)
                } else {
                    sendRandomEnglishWord(AnswerState.WrongAnswer(correctAnswer = correctKoreanTranslation))
                }
            }
        }
    }

    sealed class QuizUiState {
        data class GetWords(
            val englishWord: String,
            val defaultWord: String,
            val wasAnswerCorrect: AnswerState
        ) : QuizUiState()
    }

    sealed class AnswerState {
        data class WrongAnswer(val correctAnswer: String, val answer: Answer = Answer.INCORRECT) :
            AnswerState()

        data class CorrectAnswer(val answer: Answer = Answer.CORRECT) : AnswerState()
        object Init : AnswerState()
        object Finished : AnswerState()
    }

    sealed class Answer {
        object CORRECT : Answer()
        object INCORRECT : Answer()
    }
}