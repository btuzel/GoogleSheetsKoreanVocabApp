package com.example.googlesheetskoreanvocabapp.usefulphrases

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
class PhrasesViewModel @Inject constructor(
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
    private val _displayAllPhrasesUiState = MutableStateFlow(AllPhrases(Pair(listOf(), listOf())))
    val displayAllPhrasesUiState: StateFlow<AllPhrases> = _displayAllPhrasesUiState
    private lateinit var listOfPhrases: Pair<List<String>, List<String>>

    init {
        viewModelScope.launch {
            listOfPhrases = getWordPair(SheetsHelper.WordType.USEFUL_PHRASES)
            _displayAllPhrasesUiState.value = AllPhrases(listOfPhrases)
            sendRandomEnglishWord(AnswerState.Init)
        }
    }

    fun deletePhrasesFromColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            deleteWordPair(englishWord, koreanWord, SheetsHelper.WordType.USEFUL_PHRASES)
            listOfPhrases = getWordPair(SheetsHelper.WordType.USEFUL_PHRASES)
            _displayAllPhrasesUiState.value = AllPhrases(listOfPhrases)
        }
    }

    private val shownWords = mutableSetOf<String>()
    private fun getRandomEnglishWord(): String {
        return if(shownWords.isNotEmpty()) {
            val filteredList = listOfPhrases.first.filter { it !in shownWords }
            filteredList.random()
        } else {
            val randomWord = listOfPhrases.first.random()
            shownWords.add(randomWord)
            randomWord
        }
    }

    private fun sendRandomEnglishWord(wasAnswerCorrect: AnswerState) {
        _uiState.value = GetWords(getRandomEnglishWord(), "", wasAnswerCorrect)
    }


    fun addPhrasesToColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            addWordPair(englishWord, koreanWord, SheetsHelper.WordType.USEFUL_PHRASES)
        }
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
                    listOfPhrases.first.zip(listOfPhrases.second)
                            .find { it.first == englishWord.split("[")[0] }!!.second
            if (shownWords.size == listOfPhrases.first.size) {
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

    data class AllPhrases(val allPhrases: Pair<List<String>, List<String>>)

}