package com.example.googlesheetskoreanvocabapp.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.common.state.AnswerState
import com.example.googlesheetskoreanvocabapp.common.state.DisplayState
import com.example.googlesheetskoreanvocabapp.common.state.GetWords
import com.example.googlesheetskoreanvocabapp.data.AddWordPair
import com.example.googlesheetskoreanvocabapp.data.DeleteWordPair
import com.example.googlesheetskoreanvocabapp.data.GetWordPair
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseWordPairViewModel(
    private val getWordPair: GetWordPair,
    private val deleteWordPair: DeleteWordPair,
    private val addWordPair: AddWordPair,
    override val wordType: SheetsHelper.WordType
) : ViewModel(), WordPairViewModel {
    private lateinit var _wordPairs: Pair<List<String>, List<String>>
    override val wordPairs: Pair<List<String>, List<String>> get() = _wordPairs
    private val _displayAllPairsUiState: MutableStateFlow<DisplayState> =
        MutableStateFlow(DisplayState.Loading)
    override val displayAllPairsUiState: StateFlow<DisplayState> get() = _displayAllPairsUiState
    private val _uiState = MutableStateFlow(
        GetWords(
            englishWord = "",
            defaultWord = "",
            wasAnswerCorrect = AnswerState.Init,
            remainingPairs = 0
        )
    )
    override val uiState: StateFlow<GetWords> = _uiState

    private val shownWords = mutableSetOf<String>()

    override fun addWordPair(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            addWordPair(englishWord, koreanWord, wordType)
            _wordPairs = getWordPair(wordType)
            _displayAllPairsUiState.value = DisplayState.AllPairs(_wordPairs)
        }
    }

    override fun deleteWordPair(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            deleteWordPair(englishWord, koreanWord, wordType)
        }
    }

    fun koreanWordChanged(koreanTranslation: String) {
        _uiState.value = _uiState.value.copy(defaultWord = koreanTranslation)
    }

    fun setStateToInit() {
        _uiState.value = _uiState.value.copy(wasAnswerCorrect = AnswerState.Init)
    }

    private fun getRandomEnglishWord(): String {
        return if (shownWords.isNotEmpty()) {
            val filteredList = wordPairs.first.filter { it !in shownWords }
            val randomWord = filteredList.random()
            shownWords.add(randomWord)
            return randomWord
        } else {
            val randomWord = wordPairs.first.random()
            shownWords.add(randomWord)
            randomWord
        }
    }

    private fun sendRandomEnglishWord(wasAnswerCorrect: AnswerState) {
        _uiState.value = GetWords(getRandomEnglishWord(), "", wasAnswerCorrect, wordPairs.first.filter { it !in shownWords }.size)
    }

    init {
        viewModelScope.launch {
            _wordPairs = getWordPair(wordType)
            _displayAllPairsUiState.value = DisplayState.AllPairs(_wordPairs)
            sendRandomEnglishWord(AnswerState.Init)
        }
    }

    fun checkAnswer(englishWord: String, koreanTranslation: String) {
        viewModelScope.launch {
            val correctKoreanTranslation =
                _wordPairs.first.zip(_wordPairs.second)
                    .find { it.first == englishWord }!!.second
            if (shownWords.size == _wordPairs.first.size) {
                _uiState.value = GetWords("", "", AnswerState.Finished, 0)
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
}