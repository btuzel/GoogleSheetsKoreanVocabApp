package com.example.googlesheetskoreanvocabapp.nouns

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
class NounsViewModel @Inject constructor(
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
    private lateinit var listOfNouns: Pair<List<String>, List<String>>
    private val _displayAllNounsUiState = MutableStateFlow(AllNouns(Pair(listOf(), listOf())))
    val displayAllNounsUiState: StateFlow<AllNouns> = _displayAllNounsUiState

    init {
        viewModelScope.launch {
            listOfNouns = getWordPair(SheetsHelper.WordType.NOUNS)
            _displayAllNounsUiState.value = AllNouns(listOfNouns)
            sendRandomEnglishWord(AnswerState.Init)
        }
    }

    fun deleteNounsFromColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            deleteWordPair(englishWord, koreanWord, SheetsHelper.WordType.NOUNS)
            listOfNouns = getWordPair(SheetsHelper.WordType.NOUNS)
            _displayAllNounsUiState.value = AllNouns(listOfNouns)
        }
    }

    private val shownWords = mutableSetOf<String>()

    private fun getRandomEnglishWord(): String {
        return if (shownWords.isNotEmpty()) {
            val filteredList = listOfNouns.first.filter { it !in shownWords }
            filteredList.random()
        } else {
            val randomWord = listOfNouns.first.random()
            shownWords.add(randomWord)
            randomWord
        }
    }

    fun addNounsToColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            addWordPair(englishWord, koreanWord, SheetsHelper.WordType.NOUNS)
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
                listOfNouns.first.zip(listOfNouns.second)
                    .find { it.first == englishWord }!!.second
            if (shownWords.size == listOfNouns.first.size) {
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

    data class AllNouns(val allNouns: Pair<List<String>, List<String>>)

}