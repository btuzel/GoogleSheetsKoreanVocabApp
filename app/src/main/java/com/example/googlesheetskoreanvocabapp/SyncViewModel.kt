package com.example.googlesheetskoreanvocabapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val verbRepository: VerbRepository,
    private val sheetsHelper: SheetsHelper
) : ViewModel() {

    init {
        viewModelScope.launch {
            syncShit()
        }
    }

    private suspend fun syncShit() {
        //syncVerb
        if (isOnline()) {
            viewModelScope.launch {
                val dbVerbs = verbRepository.getVerbs()
                val getVerbsFromSheets = sheetsHelper.getWordsFromSpreadsheet(SheetsHelper.WordType.VERBS)
                val sheetsEngVerbs =
                    getVerbsFromSheets.first!!.map { it.toString().replace("[", "").replace("]", "") }
                val sheetsKorVerbs =
                    getVerbsFromSheets.second!!.map { it.toString().replace("[", "").replace("]", "") }
                if (dbVerbs.first.size > sheetsEngVerbs.size) {
                    //this means theres stuff in DB that doesnt exist in sheets, meaning data was added when offline.
                    val differencesInEngWords =
                        dbVerbs.first.subtract(sheetsEngVerbs.toSet()).toList()
                    val differencesInKorWords =
                        dbVerbs.second.subtract(sheetsKorVerbs.toSet()).toList()
                    differencesInEngWords.forEachIndexed { index, englishWord ->
                        sheetsHelper.addData(
                            SheetsHelper.WordType.VERBS,
                            Pair(englishWord, differencesInKorWords[index])
                        )
                    }
                }

                val dbAdverbs = verbRepository.getAdverbs()
                val getAdverbsFromSheets =
                    sheetsHelper.getWordsFromSpreadsheet(SheetsHelper.WordType.ADVERBS)
                val sheetsEngAdverbs =
                    getAdverbsFromSheets.first!!.map { it.toString().replace("[", "").replace("]", "") }
                val sheetsKorAdverbs =
                    getAdverbsFromSheets.second!!.map { it.toString().replace("[", "").replace("]", "") }
                if (dbAdverbs.first.size > sheetsEngAdverbs.size) {
                    //this means theres stuff in DB that doesnt exist in sheets, meaning data was added when offline.
                    val differencesInEngWords =
                        dbAdverbs.first.subtract(sheetsEngAdverbs.toSet()).toList()
                    val differencesInKorWords =
                        dbAdverbs.second.subtract(sheetsKorAdverbs.toSet()).toList()
                    differencesInEngWords.forEachIndexed { index, englishWord ->
                        sheetsHelper.addData(
                            SheetsHelper.WordType.ADVERBS,
                            Pair(englishWord, differencesInKorWords[index])
                        )
                    }
                }

                val dbNouns = verbRepository.getNouns()
                val getNounsFromSheets = sheetsHelper.getWordsFromSpreadsheet(SheetsHelper.WordType.NOUNS)
                val sheetsEngNouns =
                    getNounsFromSheets.first!!.map { it.toString().replace("[", "").replace("]", "") }
                val sheetsKorNouns =
                    getNounsFromSheets.second!!.map { it.toString().replace("[", "").replace("]", "") }
                if (dbNouns.first.size > sheetsEngNouns.size) {
                    //this means theres stuff in DB that doesnt exist in sheets, meaning data was added when offline.
                    val differencesInEngWords =
                        dbNouns.first.subtract(sheetsEngNouns.toSet()).toList()
                    val differencesInKorWords =
                        dbNouns.second.subtract(sheetsKorNouns.toSet()).toList()
                    differencesInEngWords.forEachIndexed { index, englishWord ->
                        sheetsHelper.addData(
                            SheetsHelper.WordType.NOUNS,
                            Pair(englishWord, differencesInKorWords[index])
                        )
                    }
                }

                val dbPhrases = verbRepository.getPhrases()
                val getPhrasesFromSheets = sheetsHelper.getWordsFromSpreadsheet(SheetsHelper.WordType.USEFUL_PHRASES)
                val sheetsEngPhrases =
                    getPhrasesFromSheets.first!!.map { it.toString().replace("[", "").replace("]", "") }
                val sheetsKorPhrases =
                    getPhrasesFromSheets.second!!.map { it.toString().replace("[", "").replace("]", "") }
                if (dbPhrases.first.size > sheetsEngPhrases.size) {
                    //this means theres stuff in DB that doesnt exist in sheets, meaning data was added when offline.
                    val differencesInEngWords =
                        dbPhrases.first.subtract(sheetsEngPhrases.toSet()).toList()
                    val differencesInKorWords =
                        dbPhrases.second.subtract(sheetsKorPhrases.toSet()).toList()
                    differencesInEngWords.forEachIndexed { index, englishWord ->
                        sheetsHelper.addData(
                            SheetsHelper.WordType.USEFUL_PHRASES,
                            Pair(englishWord, differencesInKorWords[index])
                        )
                    }
                }

                val dbPositions = verbRepository.getPositions()
                val getPositionsFromSheets = sheetsHelper.getWordsFromSpreadsheet(SheetsHelper.WordType.POSITIONS)
                val sheetsEngPositions =
                    getPositionsFromSheets.first!!.map { it.toString().replace("[", "").replace("]", "") }
                val sheetsKorPositions =
                    getPositionsFromSheets.second!!.map { it.toString().replace("[", "").replace("]", "") }
                if (dbPositions.first.size > sheetsEngPositions.size) {
                    //this means theres stuff in DB that doesnt exist in sheets, meaning data was added when offline.
                    val differencesInEngWords =
                        dbPositions.first.subtract(sheetsEngPositions.toSet()).toList()
                    val differencesInKorWords =
                        dbPositions.second.subtract(sheetsKorPositions.toSet()).toList()
                    differencesInEngWords.forEachIndexed { index, englishWord ->
                        sheetsHelper.addData(
                            SheetsHelper.WordType.POSITIONS,
                            Pair(englishWord, differencesInKorWords[index])
                        )
                    }
                }

                val dbSentences = verbRepository.getSentences()
                val getSentencesFromSheets = sheetsHelper.getWordsFromSpreadsheet(SheetsHelper.WordType.COMPLEX_SENTENCES)
                val sheetsEngSentences =
                    getSentencesFromSheets.first!!.map { it.toString().replace("[", "").replace("]", "") }
                val sheetsKorSentences =
                    getSentencesFromSheets.second!!.map { it.toString().replace("[", "").replace("]", "") }
                if (dbSentences.first.size > sheetsEngSentences.size) {
                    //this means theres stuff in DB that doesnt exist in sheets, meaning data was added when offline.
                    val differencesInEngWords =
                        dbSentences.first.subtract(sheetsEngSentences.toSet()).toList()
                    val differencesInKorWords =
                        dbSentences.second.subtract(sheetsKorSentences.toSet()).toList()
                    differencesInEngWords.forEachIndexed { index, englishWord ->
                        sheetsHelper.addData(
                            SheetsHelper.WordType.COMPLEX_SENTENCES,
                            Pair(englishWord, differencesInKorWords[index])
                        )
                    }
                }
            }
        }
    }
}
