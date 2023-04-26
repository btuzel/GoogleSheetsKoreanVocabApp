package com.example.googlesheetskoreanvocabapp.data

import com.example.googlesheetskoreanvocabapp.db.*
import com.example.googlesheetskoreanvocabapp.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteWordPair @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(
        englishWord: String,
        koreanWord: String,
        wordType: SheetsHelper.WordType
    ) = withContext(
        Dispatchers.IO
    ) {
        when (wordType) {
            SheetsHelper.WordType.VERBS -> {
                verbRepository.deleteVerb(
                    Verbs(
                        englishWord = englishWord,
                        koreanWord = koreanWord
                    )
                )
            }
            SheetsHelper.WordType.ADVERBS -> {
                verbRepository.deleteAdverbs(
                    Adverbs(
                        englishWord = englishWord,
                        koreanWord = koreanWord
                    )
                )
            }
            SheetsHelper.WordType.COMPLEX_SENTENCES -> {
                verbRepository.deleteSentence(
                    Sentences(
                        englishWord = englishWord,
                        koreanWord = koreanWord
                    )
                )
            }
            SheetsHelper.WordType.USEFUL_PHRASES -> {
                verbRepository.deletePhrases(
                    Phrases(
                        englishWord = englishWord,
                        koreanWord = koreanWord
                    )
                )
            }
            SheetsHelper.WordType.NOUNS -> {
                verbRepository.deleteNouns(
                    Nouns(
                        englishWord = englishWord,
                        koreanWord = koreanWord
                    )
                )
            }
            SheetsHelper.WordType.POSITIONS -> {
                verbRepository.deletePositions(
                    Positions(
                        englishWord = englishWord,
                        koreanWord = koreanWord
                    )
                )
            }
        }
        if (isOnline()) {
            when (wordType) {
                SheetsHelper.WordType.VERBS -> {
                    sheetsHelper.deleteData(
                        SheetsHelper.WordType.VERBS,
                        Pair(englishWord, koreanWord)
                    )
                }
                SheetsHelper.WordType.ADVERBS -> {
                    sheetsHelper.deleteData(
                        SheetsHelper.WordType.ADVERBS,
                        Pair(englishWord, koreanWord)
                    )
                }
                SheetsHelper.WordType.COMPLEX_SENTENCES -> {
                    sheetsHelper.deleteData(
                        SheetsHelper.WordType.COMPLEX_SENTENCES,
                        Pair(englishWord, koreanWord)
                    )
                }
                SheetsHelper.WordType.USEFUL_PHRASES -> {
                    sheetsHelper.deleteData(
                        SheetsHelper.WordType.USEFUL_PHRASES,
                        Pair(englishWord, koreanWord)
                    )
                }
                SheetsHelper.WordType.NOUNS -> {
                    sheetsHelper.deleteData(
                        SheetsHelper.WordType.NOUNS,
                        Pair(englishWord, koreanWord)
                    )
                }
                SheetsHelper.WordType.POSITIONS -> {
                    sheetsHelper.deleteData(
                        SheetsHelper.WordType.POSITIONS,
                        Pair(englishWord, koreanWord)
                    )
                }
            }
        }
    }
}