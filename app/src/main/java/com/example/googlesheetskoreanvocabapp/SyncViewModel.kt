package com.example.googlesheetskoreanvocabapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val verbRepository: VerbRepository,
    private val sheetsHelper: SheetsHelper
) : ViewModel() {

    init {
        viewModelScope.launch {
            syncAllData()
        }
    }

    private suspend fun syncAllData() {
        if (isOnline()) {
            viewModelScope.launch {
                syncWords(SheetsHelper.WordType.VERBS)
                syncWords(SheetsHelper.WordType.NOUNS)
                syncWords(SheetsHelper.WordType.ADVERBS)
                syncWords(SheetsHelper.WordType.USEFUL_PHRASES)
                syncWords(SheetsHelper.WordType.POSITIONS)
                syncWords(SheetsHelper.WordType.COMPLEX_SENTENCES)
            }
        }
    }

    private suspend fun syncWords(wordType: SheetsHelper.WordType) {
        val dbWords = when (wordType) {
            SheetsHelper.WordType.VERBS -> verbRepository.getVerbs()
            SheetsHelper.WordType.NOUNS -> verbRepository.getNouns()
            SheetsHelper.WordType.ADVERBS -> verbRepository.getAdverbs()
            SheetsHelper.WordType.USEFUL_PHRASES -> verbRepository.getPhrases()
            SheetsHelper.WordType.POSITIONS -> verbRepository.getPositions()
            SheetsHelper.WordType.COMPLEX_SENTENCES -> verbRepository.getSentences()
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
                sheetsHelper.addData(
                    wordType,
                    Pair(englishWord, differencesInKorWords[index])
                )
            }
        }
    }
}
