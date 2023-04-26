package com.example.googlesheetskoreanvocabapp.verbs

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
class VerbsViewModel @Inject constructor(
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
    private val _displayAllVerbsUiState = MutableStateFlow(AllVerbs(Pair(listOf(), listOf())))
    val displayAllVerbsUiState: StateFlow<AllVerbs> = _displayAllVerbsUiState
    private lateinit var listOfVerbs: Pair<List<String>, List<String>>

    init {
        viewModelScope.launch {
            listOfVerbs = getWordPair(SheetsHelper.WordType.VERBS)
            _displayAllVerbsUiState.value = AllVerbs(listOfVerbs)
            sendRandomEnglishWord(AnswerState.Init)
        }
    }

    private val shownWords = mutableSetOf<String>()

    private fun getRandomEnglishWord(): String {
        return if (shownWords.isNotEmpty()) {
            val filteredList = listOfVerbs.first.filter { it !in shownWords }
            filteredList.random()
        } else {
            val randomWord = listOfVerbs.first.random()
            shownWords.add(randomWord)
            randomWord
        }
    }

    fun deleteVerbsFromColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            deleteWordPair(englishWord, koreanWord, SheetsHelper.WordType.VERBS)
            listOfVerbs = getWordPair(SheetsHelper.WordType.VERBS)
            _displayAllVerbsUiState.value = AllVerbs(listOfVerbs)
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
                listOfVerbs.first.zip(listOfVerbs.second)
                    .find { it.first == englishWord.split("[")[0] }!!.second
            if (shownWords.size == listOfVerbs.first.size) {
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

    fun addVerbToColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            addWordPair(englishWord, koreanWord, SheetsHelper.WordType.VERBS)
        }
    }

    data class AllVerbs(val allVerbs: Pair<List<String>, List<String>>)
}