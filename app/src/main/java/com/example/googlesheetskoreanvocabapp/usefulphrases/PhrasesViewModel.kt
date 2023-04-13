package com.example.googlesheetskoreanvocabapp.usefulphrases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val initialUiState = QuizUiState.GetWords(
        englishWord = "",
        defaultWord = "",
        wasAnswerCorrect = AnswerState.Init
    )
    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<QuizUiState> = _uiState
    private val _uiState3 = MutableStateFlow(AllPhrases(Pair(listOf(), listOf())))
    val uiState3: StateFlow<AllPhrases> = _uiState3
    private lateinit var listOfPhrases: Pair<List<String>, List<String>>

    init {
        viewModelScope.launch {
            listOfPhrases = getWordPair(SheetsHelper.WordType.USEFUL_PHRASES)
            _uiState3.value = AllPhrases(listOfPhrases)
            sendRandomEnglishWord(AnswerState.Init)
        }
    }

    fun deletePhrasesFromColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            deleteWordPair(englishWord, koreanWord, SheetsHelper.WordType.USEFUL_PHRASES)
        }
    }

    private val shownWords = mutableSetOf<String>()
    private fun getRandomEnglishWord(): String {
        val randomWord = listOfPhrases.first.random()
        shownWords.add(randomWord)
        return randomWord
    }

    private fun sendRandomEnglishWord(wasAnswerCorrect: AnswerState) {
        _uiState.value = QuizUiState.GetWords(getRandomEnglishWord(), "", wasAnswerCorrect)
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

    data class AllPhrases(val allPhrases: Pair<List<String>, List<String>>)


    sealed class Answer {
        object CORRECT : Answer()
        object INCORRECT : Answer()
    }
}