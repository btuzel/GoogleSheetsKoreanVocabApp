package com.example.googlesheetskoreanvocabapp.common.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.common.state.AnswerState
import com.example.googlesheetskoreanvocabapp.common.state.DisplayState
import com.example.googlesheetskoreanvocabapp.common.state.GetWords
import com.example.googlesheetskoreanvocabapp.common.ui.SaveResultState
import com.example.googlesheetskoreanvocabapp.data.AddWordPair
import com.example.googlesheetskoreanvocabapp.data.DeleteWordPair
import com.example.googlesheetskoreanvocabapp.data.GetWordPair
import com.example.googlesheetskoreanvocabapp.data.SaveResult
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates

abstract class BaseWordPairViewModel(
    private val getWordPair: GetWordPair,
    private val deleteWordPair: DeleteWordPair,
    private val addWordPair: AddWordPair,
    private val saveResultUseCase: SaveResult,
    override val wordType: SheetsHelper.WordType
) : ViewModel(), WordPairViewModel {

    lateinit var wordLimitOffset: WordLimitOffset

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

    private val _wordState: MutableStateFlow<WordState> = MutableStateFlow(
        WordState.AddWordState("", false)
    )
    val wordState: StateFlow<WordState> = _wordState

    private val shownWords = mutableSetOf<String>()
    private val incorrectWords = mutableListOf<String>()

    private lateinit var startTime: LocalDateTime

    override fun addWordPair(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            val awp = addWordPair(englishWord, koreanWord, wordType)
            _wordState.value = WordState.AddWordState(englishWord, awp)
            _wordPairs = getWordPair(wordType, 0, 3000)
            _displayAllPairsUiState.value = DisplayState.AllPairs(_wordPairs)
        }
    }

    override fun deleteWordPair(englishWord: String, koreanWord: String) {
        viewModelScope.launch {
            val dwp = deleteWordPair(englishWord, koreanWord, wordType)
            _wordState.value = WordState.DeleteWordState(englishWord, dwp)
            _wordPairs = getWordPair(wordType, 0 , 3000)
            _displayAllPairsUiState.value = DisplayState.AllPairs(_wordPairs)
        }
    }

    fun koreanWordChanged(koreanTranslation: String) {
        _uiState.value = _uiState.value.copy(defaultWord = koreanTranslation)
    }

    fun setStateToInit() {
        _uiState.value = _uiState.value.copy(wasAnswerCorrect = AnswerState.Init)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun saveResult(savedResultState: SaveResultState) {
        val now = LocalDateTime.now()
        val formattedDateTime =
            now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val durationMin = Duration.between(startTime, now).toMinutes()
        val durationSec = Duration.between(startTime, now).toSeconds() % 60
        viewModelScope.launch {
            val saveResultState = SaveResultCompleteState(
                savedResultState,
                formattedDateTime,
                durationMin.toString(),
                durationSec.toString(),
                incorrectWords.ifEmpty { listOf() }
            )
            saveResultUseCase(saveResultState)
        }
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
        _uiState.value = GetWords(
            getRandomEnglishWord(),
            "",
            wasAnswerCorrect,
            wordPairs.first.filter { it !in shownWords }.size + 1
        )
    }

    init {
        viewModelScope.launch {
            delay(1000)
            _wordPairs = getWordPair(
                wordType = wordType,
                offset = wordLimitOffset.offset,
                limit = wordLimitOffset.limit
            )
            _displayAllPairsUiState.value = DisplayState.AllPairs(_wordPairs)
            sendRandomEnglishWord(AnswerState.Init)
            startTime = LocalDateTime.now()
        }
    }

    fun checkAnswer(englishWord: String, koreanTranslation: String) {
        viewModelScope.launch {
            val correctKoreanTranslation =
                _wordPairs.first.zip(_wordPairs.second)
                    .find { it.first == englishWord }!!.second
            if (shownWords.size == _wordPairs.first.size && correctKoreanTranslation == koreanTranslation) {
                _uiState.value = GetWords("", "", AnswerState.Finished, 0)
            } else {
                if (correctKoreanTranslation == koreanTranslation) {
                    sendRandomEnglishWord(AnswerState.CorrectAnswer())
                } else {
                    shownWords.remove(englishWord)
                    incorrectWords.add(englishWord)
                    sendRandomEnglishWord(AnswerState.WrongAnswer(correctAnswer = correctKoreanTranslation))
                }
            }
        }
    }

    sealed interface WordState {
        data class AddWordState(val word: String, val added: Boolean) : WordState
        data class DeleteWordState(val word: String, val added: Boolean) : WordState
    }

    data class SaveResultCompleteState(
        val savedResultState: SaveResultState,
        val formattedDateTime: String,
        val minDuration: String,
        val secDuration: String,
        val incorrectStrings: List<String>,
    )

    data class WordLimitOffset(
        val limit: Int,
        val offset: Int
    )

}
