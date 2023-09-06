package com.example.googlesheetskoreanvocabapp

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.common.fixStrings
import com.example.googlesheetskoreanvocabapp.common.isOnline
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.Hyungseok
import com.example.googlesheetskoreanvocabapp.db.MainRepositoryDatabase
import com.example.googlesheetskoreanvocabapp.db.OldWords
import com.example.googlesheetskoreanvocabapp.db.Repeatables
import com.example.googlesheetskoreanvocabapp.db.Yuun
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SyncViewModel @Inject constructor(
    private val mainRepositoryDatabase: MainRepositoryDatabase,
    private val sheetsHelper: SheetsHelper,
    private val sharedPreferences: SharedPreferences,
    @ApplicationContext private val context: Context
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
                _uiState.value = SyncState.Loading(SheetsHelper.WordType.YUUN, 1 / 4f)
                syncWords(SheetsHelper.WordType.YUUN)
                _uiState.value = SyncState.Loading(SheetsHelper.WordType.REPEATABLES, 2 / 4f)
                syncWords(SheetsHelper.WordType.REPEATABLES)
                _uiState.value = SyncState.Loading(SheetsHelper.WordType.OLDWORDS, 3 / 4f)
                syncWords(SheetsHelper.WordType.OLDWORDS)
                _uiState.value = SyncState.Loading(SheetsHelper.WordType.HYUNGSEOK, 4 / 4f)
                syncWords(SheetsHelper.WordType.HYUNGSEOK)
                _uiState.value = SyncState.Done
            }
        } else {
            _uiState.value = SyncState.Done
        }
    }

    private suspend fun syncWords(wordType: SheetsHelper.WordType) {
        val dbWords = when (wordType) {
            SheetsHelper.WordType.YUUN -> mainRepositoryDatabase.getYuuns()
            SheetsHelper.WordType.REPEATABLES -> mainRepositoryDatabase.getRepeatables()
            SheetsHelper.WordType.OLDWORDS -> mainRepositoryDatabase.getOldWords()
            SheetsHelper.WordType.HYUNGSEOK -> mainRepositoryDatabase.getHyungseoks()
        }
        val getWordsFromSheets = sheetsHelper.getWordsFromSpreadsheet(wordType)
        val sheetsEngWords =
            getWordsFromSheets.first!!.map { it.toString().fixStrings() }
        val sheetsKorWords =
            getWordsFromSheets.second!!.map { it.toString().fixStrings() }
        val changedKoreanWords = dbWords.second.subtract(sheetsKorWords.toSet())
        if (changedKoreanWords.isNotEmpty()) {
            changedKoreanWords.forEach {
                val indexOfChangedKoreanWordInEnglish = dbWords.second.indexOf(it)
                val changedWordsEnglishWord = dbWords.first[indexOfChangedKoreanWordInEnglish]
                when (wordType) {
                    SheetsHelper.WordType.YUUN -> mainRepositoryDatabase.deleteYuuns(
                        Yuun(
                            englishWord = changedWordsEnglishWord,
                            koreanWord = it
                        )
                    )

                    SheetsHelper.WordType.REPEATABLES -> mainRepositoryDatabase.deleteRepeatables(
                        Repeatables(
                            englishWord = changedWordsEnglishWord,
                            koreanWord = it
                        )
                    )

                    SheetsHelper.WordType.OLDWORDS -> mainRepositoryDatabase.deleteOldWords(
                        OldWords(
                            englishWord = changedWordsEnglishWord,
                            koreanWord = it
                        )
                    )

                    SheetsHelper.WordType.HYUNGSEOK -> mainRepositoryDatabase.deleteHyungseoks(
                        Hyungseok(
                            englishWord = changedWordsEnglishWord,
                            koreanWord = it
                        )
                    )
                }
            }
        }
        if (dbWords.first.size > sheetsEngWords.size) {
            val differencesInEngWords =
                dbWords.first.subtract(sheetsEngWords.toSet()).toList()
            val differencesInKorWords =
                dbWords.second.subtract(sheetsKorWords.toSet()).toList()
            try {
                differencesInEngWords.forEachIndexed { index, englishWord ->
                    when (wordType) {
                        SheetsHelper.WordType.YUUN -> mainRepositoryDatabase.deleteYuuns(
                            Yuun(
                                englishWord = englishWord,
                                koreanWord = differencesInKorWords[index]
                            )
                        )

                        SheetsHelper.WordType.REPEATABLES -> mainRepositoryDatabase.deleteRepeatables(
                            Repeatables(
                                englishWord = englishWord,
                                koreanWord = differencesInKorWords[index]
                            )
                        )

                        SheetsHelper.WordType.OLDWORDS -> mainRepositoryDatabase.deleteOldWords(
                            OldWords(
                                englishWord = englishWord,
                                koreanWord = differencesInKorWords[index]
                            )
                        )

                        SheetsHelper.WordType.HYUNGSEOK -> mainRepositoryDatabase.deleteHyungseoks(
                            Hyungseok(
                                englishWord = englishWord,
                                koreanWord = differencesInKorWords[index]
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                clearCache()
                clearData()
            }
        }
    }

    private fun clearCache() {
        val packageName = context.packageName
        val cacheDir = context.cacheDir

        val cacheFiles = cacheDir.listFiles { file ->
            file.isDirectory && file.name.startsWith(packageName)
        }

        cacheFiles?.forEach { file ->
            file.deleteRecursively()
        }
    }

    private fun clearData() {
        viewModelScope.launch {
            (context.getSystemService(ACTIVITY_SERVICE) as ActivityManager).clearApplicationUserData()
        }
    }

    sealed class SyncState {
        object Done : SyncState()
        object Init : SyncState()
        data class Loading(val wordType: SheetsHelper.WordType, val percentage: Float) : SyncState()
    }
}
