package com.example.googlesheetskoreanvocabapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.common.fixStrings
import com.example.googlesheetskoreanvocabapp.common.isOnline
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.Adverbs
import com.example.googlesheetskoreanvocabapp.db.Nouns
import com.example.googlesheetskoreanvocabapp.db.Phrases
import com.example.googlesheetskoreanvocabapp.db.Positions
import com.example.googlesheetskoreanvocabapp.db.Sentences
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.db.Verbs
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
                when (wordType) {
                    SheetsHelper.WordType.VERBS -> verbRepository.deleteVerb(
                        Verbs(
                            englishWord = englishWord,
                            koreanWord = differencesInKorWords[index]
                        )
                    )

                    SheetsHelper.WordType.ADVERBS -> verbRepository.deleteAdverbs(
                        Adverbs(
                            englishWord = englishWord,
                            koreanWord = differencesInKorWords[index]
                        )
                    )

                    SheetsHelper.WordType.COMPLEX_SENTENCES -> verbRepository.deleteSentence(
                        Sentences(
                            englishWord = englishWord,
                            koreanWord = differencesInKorWords[index]
                        )
                    )

                    SheetsHelper.WordType.USEFUL_PHRASES -> verbRepository.deletePhrases(
                        Phrases(
                            englishWord = englishWord,
                            koreanWord = differencesInKorWords[index]
                        )
                    )

                    SheetsHelper.WordType.NOUNS -> verbRepository.deleteNouns(
                        Nouns(
                            englishWord = englishWord,
                            koreanWord = differencesInKorWords[index]
                        )
                    )

                    SheetsHelper.WordType.POSITIONS -> verbRepository.deletePositions(
                        Positions(
                            englishWord = englishWord,
                            koreanWord = differencesInKorWords[index]
                        )
                    )
                }
            }
        }
    }
}
