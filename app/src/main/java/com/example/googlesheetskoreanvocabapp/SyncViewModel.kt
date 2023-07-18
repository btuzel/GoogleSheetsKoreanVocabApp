package com.example.googlesheetskoreanvocabapp

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.common.VerbGroupType
import com.example.googlesheetskoreanvocabapp.common.VerbInstance
import com.example.googlesheetskoreanvocabapp.common.fixStrings
import com.example.googlesheetskoreanvocabapp.common.isOnline
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.db.Verbs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val verbRepository: VerbRepository,
    private val sheetsHelper: SheetsHelper,
    private val sharedPreferences: SharedPreferences,
    private val verbInstance: VerbInstance
) : ViewModel() {

    private val _uiState: MutableStateFlow<SyncState> = MutableStateFlow(
        SyncState.Init
    )
    val uiState: StateFlow<SyncState> = _uiState

    init {
        viewModelScope.launch {
            syncAllData()
        }
    }

    fun clearSharedPref() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private suspend fun syncAllData() {
        if (isOnline()) {
            viewModelScope.launch {
                _uiState.value = SyncState.Loading(SheetsHelper.WordType.VERBS, 6 / 6f)
                syncWords(SheetsHelper.WordType.VERBS)
                _uiState.value = SyncState.Done
            }
        } else {
            _uiState.value = SyncState.Done
        }
    }

    private suspend fun syncWords(wordType: SheetsHelper.WordType) {
        val dbWords = when (wordType) {
            SheetsHelper.WordType.VERBS -> verbRepository.getVerbs()
        }
        val getWordsFromSheets = sheetsHelper.getWordsFromSpreadsheet(wordType)
        val sheetsEngWords =
            getWordsFromSheets.first!!.map { it.toString().fixStrings() }
        val sheetsKorWords =
            getWordsFromSheets.second!!.map { it.toString().fixStrings() }
        if (dbWords.first.size > sheetsEngWords.size) {
            val differencesInEngWords =
                dbWords.first.subtract(sheetsEngWords.toSet()).toList()
            val differencesInKorWords =
                dbWords.second.subtract(sheetsKorWords.toSet()).toList()
            differencesInEngWords.forEachIndexed { index, englishWord ->
                when (wordType) {
                    SheetsHelper.WordType.VERBS -> verbRepository.deleteVerb(
                        Verbs(
                            englishWord = englishWord,
                            koreanWord = differencesInKorWords[index]
                        )
                    )
                }
            }
        }
    }

    fun setVerbGroup(verbGroupType: VerbGroupType) {
        verbInstance.setVerbGroupType(verbGroupType)
    }

    sealed class SyncState {
        object Done : SyncState()
        object Init : SyncState()
        data class Loading(val wordType: SheetsHelper.WordType, val percentage: Float) : SyncState()
    }
}
