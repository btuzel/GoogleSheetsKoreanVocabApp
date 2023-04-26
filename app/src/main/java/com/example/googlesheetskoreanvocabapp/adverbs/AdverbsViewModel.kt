package com.example.googlesheetskoreanvocabapp.adverbs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.common.AnswerState
import com.example.googlesheetskoreanvocabapp.data.AddWordPair
import com.example.googlesheetskoreanvocabapp.data.DeleteWordPair
import com.example.googlesheetskoreanvocabapp.data.GetWordPair
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdverbsViewModel @Inject constructor(
    private val getWordPair: GetWordPair,
    private val addWordPair: AddWordPair,
    private val deleteWordPair: DeleteWordPair
) : ViewModel() {

    private val initialUiState = QuizUiState.GetWords(
        englishWord = "",
        defaultWord = "",
        wasAnswerCorrect = AnswerState.Init
    )
    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<QuizUiState> = _uiState
    private val _uiState3 = MutableStateFlow(AllAdverbs(Pair(listOf(), listOf())))
    val uiState3: StateFlow<AllAdverbs> = _uiState3
    private lateinit var listOfAdverbs: Pair<List<String>, List<String>>

    init {
        viewModelScope.launch {
            listOfAdverbs = getWordPair(SheetsHelper.WordType.ADVERBS)
            _uiState3.value = AllAdverbs(listOfAdverbs)
            sendRandomEnglishWord(AnswerState.Init)
        }
    }

    private val shownWords = mutableSetOf<String>()

    fun addAdverbsToColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            addWordPair(englishWord, koreanWord, SheetsHelper.WordType.ADVERBS)
            listOfAdverbs = getWordPair(SheetsHelper.WordType.ADVERBS)
            _uiState3.value = AllAdverbs(listOfAdverbs)
        }
    }

    fun deleteAdverbFromColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            deleteWordPair(englishWord, koreanWord, SheetsHelper.WordType.ADVERBS)
        }
    }

    private fun getRandomEnglishWord(): String {
        val randomWord = listOfAdverbs.first.random()
        shownWords.add(randomWord)
        return randomWord
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

    data class AllAdverbs(val allAdverbs: Pair<List<String>, List<String>>)

}