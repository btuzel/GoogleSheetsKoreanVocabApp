package com.example.googlesheetskoreanvocabapp.positions

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
class PositionsViewModel @Inject constructor(
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
    private val _uiState3 = MutableStateFlow(AllPositions(Pair(listOf(), listOf())))
    val uiState3: StateFlow<AllPositions> = _uiState3
    private lateinit var listOfPositions: Pair<List<String>, List<String>>

    init {
        viewModelScope.launch {
            listOfPositions = getWordPair(SheetsHelper.WordType.POSITIONS)
            _uiState3.value = AllPositions(listOfPositions)
            sendRandomEnglishWord(AnswerState.Init)
        }
    }

    fun deletePositionsFromColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            deleteWordPair(englishWord, koreanWord, SheetsHelper.WordType.POSITIONS)
            listOfPositions = getWordPair(SheetsHelper.WordType.POSITIONS)
            _uiState3.value = AllPositions(listOfPositions)
        }
    }

    private val shownWords = mutableSetOf<String>()

    private fun getRandomEnglishWord(): String {
        val randomWord = listOfPositions.first.random()
        shownWords.add(randomWord)
        return randomWord
    }


    fun addPositionsToColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            addWordPair(englishWord, koreanWord, SheetsHelper.WordType.POSITIONS)
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
                    listOfPositions.first.zip(listOfPositions.second)
                            .find { it.first == englishWord.split("[")[0] }!!.second
            if (shownWords.size == listOfPositions.first.size) {
                _uiState.value = GetWords("", "", AnswerState.Finished)
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


    data class GetWords(
            val englishWord: String,
            val defaultWord: String,
            val wasAnswerCorrect: AnswerState
    )


    data class AllPositions(val allPositions: Pair<List<String>, List<String>>)

}