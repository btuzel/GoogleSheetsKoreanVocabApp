package com.example.googlesheetskoreanvocabapp.complexsentences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.common.AnswerState
import com.example.googlesheetskoreanvocabapp.common.GetWords
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
class GetComplexSentencesViewModel @Inject constructor(
    private val getWordPair: GetWordPair,
    private val addWordPair: AddWordPair,
    private val deleteWordPair: DeleteWordPair
) : ViewModel() {

    private val initialUiState = GetWords(
        englishWord = "",
        defaultWord = "",
        wasAnswerCorrect = AnswerState.Init
    )
    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<GetWords> = _uiState
    private val _displayAllSentencesUiState =
        MutableStateFlow(AllSentences(Pair(listOf(), listOf())))
    val displayAllSentencesUiState: StateFlow<AllSentences> = _displayAllSentencesUiState
    private lateinit var listOfComplexSentences: Pair<List<String>, List<String>>

    init {
        viewModelScope.launch {
            listOfComplexSentences = getWordPair(SheetsHelper.WordType.COMPLEX_SENTENCES)
            _displayAllSentencesUiState.value = AllSentences(listOfComplexSentences)
            sendRandomEnglishWord(AnswerState.Init)
        }
    }

    private val shownWords = mutableSetOf<String>()

    fun addComplexSentencesToColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            addWordPair(englishWord, koreanWord, SheetsHelper.WordType.COMPLEX_SENTENCES)
        }
    }

    fun deleteComplexSentencesFromColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            deleteWordPair(englishWord, koreanWord, SheetsHelper.WordType.COMPLEX_SENTENCES)
            listOfComplexSentences = getWordPair(SheetsHelper.WordType.COMPLEX_SENTENCES)
            _displayAllSentencesUiState.value = AllSentences(listOfComplexSentences)
        }
    }


    private fun getRandomEnglishWord(): String {
        return if (shownWords.isNotEmpty()) {
            val filteredList = listOfComplexSentences.first.filter { it !in shownWords }
            filteredList.random()
        } else {
            val randomWord = listOfComplexSentences.first.random()
            shownWords.add(randomWord)
            randomWord
        }
    }

    private fun sendRandomEnglishWord(wasAnswerCorrect: AnswerState) {
        _uiState.value = GetWords(getRandomEnglishWord(), "", wasAnswerCorrect)
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
                listOfComplexSentences.first.zip(listOfComplexSentences.second)
                    .find { it.first == englishWord }!!.second
            if (shownWords.size == listOfComplexSentences.first.size) {
                _uiState.value = GetWords("", "", AnswerState.Finished)
            } else {
                if (correctKoreanTranslation == koreanTranslation) {
                    sendRandomEnglishWord(AnswerState.CorrectAnswer())
                } else {
                    shownWords.remove(englishWord)
                    sendRandomEnglishWord(AnswerState.WrongAnswer(correctAnswer = correctKoreanTranslation))
                }
            }
        }
    }

    data class AllSentences(val allSentences: Pair<List<String>, List<String>>)
}