package com.example.googlesheetskoreanvocabapp.verbs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.common.AnswerState
import com.example.googlesheetskoreanvocabapp.data.AddWordPair
import com.example.googlesheetskoreanvocabapp.data.DeleteWordPair
import com.example.googlesheetskoreanvocabapp.data.GetWordPair
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class VerbsViewModel @Inject constructor(
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
    private val _uiState3 = MutableStateFlow(AllVerbs(Pair(listOf(), listOf())))
    val uiState3: StateFlow<AllVerbs> = _uiState3
    private lateinit var listOfVerbs: Pair<List<String>, List<String>>

    init {
        viewModelScope.launch {
            listOfVerbs = getWordPair(SheetsHelper.WordType.VERBS)
            _uiState3.value = AllVerbs(listOfVerbs)
            sendRandomEnglishWord(AnswerState.Init)
        }
    }

    private val shownWords = mutableSetOf<String>()

    private fun getRandomEnglishWord(): String {
        val randomWord = listOfVerbs.first.random()
        shownWords.add(randomWord)
        return randomWord
    }

    fun deleteVerbsFromColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            deleteWordPair(englishWord, koreanWord, SheetsHelper.WordType.VERBS)
            listOfVerbs = getWordPair(SheetsHelper.WordType.VERBS)
            _uiState3.value = AllVerbs(listOfVerbs)
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
                listOfVerbs.first.zip(listOfVerbs.second)
                    .find { it.first == englishWord.split("[")[0] }!!.second
            if (shownWords.size == listOfVerbs.first.size) {
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

    fun addVerbToColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            addWordPair(englishWord, koreanWord, SheetsHelper.WordType.VERBS)
        }
    }

    sealed class QuizUiState {
        data class GetWords(
            val englishWord: String,
            val defaultWord: String,
            val wasAnswerCorrect: AnswerState
        ) : QuizUiState()

    }

    data class AllVerbs(val allVerbs: Pair<List<String>, List<String>>)
}