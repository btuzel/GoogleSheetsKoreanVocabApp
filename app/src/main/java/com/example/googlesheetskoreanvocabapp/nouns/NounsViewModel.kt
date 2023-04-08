package com.example.googlesheetskoreanvocabapp.nouns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NounsViewModel @Inject constructor(private val getNouns: GetNouns, private val addNouns: AddNouns) : ViewModel() {

    private val initialUiState = QuizUiState.GetWords(
        englishWord = "",
        defaultWord = "",
        wasAnswerCorrect = AnswerState.Init
    )
    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<QuizUiState> = _uiState
    private lateinit var listOfNouns: Pair<List<String>, List<String>>

    init {
        viewModelScope.launch {
            fixAllWords()
            sendRandomEnglishWord(AnswerState.Init)
        }
    }

    private val shownWords = mutableSetOf<String>()

    private fun getRandomEnglishWord(): String {
        val remainingWords = listOfNouns.first.filter { it !in shownWords }

        val incorrectWords = shownWords.filter { englishWord ->
            val correctKoreanTranslation = listOfNouns.first.zip(listOfNouns.second)
                .find { it.first == englishWord.split("[")[0] }!!.second
            correctKoreanTranslation != _uiState.value.defaultWord
        }

        val wordsToChooseFrom = incorrectWords.ifEmpty {
            remainingWords
        }

        val randomWord = wordsToChooseFrom.random()

        shownWords.add(randomWord)
        return randomWord
    }

    fun addNounsToColumn(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            addNouns(englishWord, koreanWord)
        }
    }

    private suspend fun fixAllWords() {
        listOfNouns = getNouns()
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
                listOfNouns.first.zip(listOfNouns.second)
                    .find { it.first == englishWord.split("[")[0] }!!.second
            if (shownWords.size == listOfNouns.first.size) {
                _uiState.value = QuizUiState.GetWords("", "", AnswerState.Finished)
            } else {
                if (correctKoreanTranslation == koreanTranslation) {
                    sendRandomEnglishWord(AnswerState.CorrectAnswer())
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