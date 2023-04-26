package com.example.googlesheetskoreanvocabapp.data

import com.example.googlesheetskoreanvocabapp.db.*
import com.example.googlesheetskoreanvocabapp.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddWordPair @Inject constructor(
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
                verbRepository.insertVerbs(
                    listOf(
                        Verbs(
                            englishWord = englishWord,
                            koreanWord = koreanWord
                        )
                    )
                )
            }
            SheetsHelper.WordType.ADVERBS -> {
                verbRepository.insertAdverbs(
                    listOf(
                        Adverbs(
                            englishWord = englishWord,
                            koreanWord = koreanWord
                        )
                    )
                )
            }
            SheetsHelper.WordType.COMPLEX_SENTENCES -> {
                verbRepository.insertSentences(
                    listOf(
                        Sentences(
                            englishWord = englishWord,
                            koreanWord = koreanWord
                        )
                    )
                )
            }
            SheetsHelper.WordType.USEFUL_PHRASES -> {
                verbRepository.insertPhrases(
                    listOf(
                        Phrases(
                            englishWord = englishWord,
                            koreanWord = koreanWord
                        )
                    )
                )
            }
            SheetsHelper.WordType.NOUNS -> {
                verbRepository.insertNouns(
                    listOf(
                        Nouns(
                            englishWord = englishWord,
                            koreanWord = koreanWord
                        )
                    )
                )
            }
            SheetsHelper.WordType.POSITIONS -> {
                verbRepository.insertPositions(
                    listOf(
                        Positions(
                            englishWord = englishWord,
                            koreanWord = koreanWord
                        )
                    )
                )
            }
        }
        if (isOnline()) {
            when (wordType) {
                SheetsHelper.WordType.VERBS -> sheetsHelper.addData(
                    SheetsHelper.WordType.VERBS,
                    Pair(englishWord, koreanWord)
                )
                SheetsHelper.WordType.ADVERBS -> sheetsHelper.addData(
                    SheetsHelper.WordType.ADVERBS,
                    Pair(englishWord, koreanWord)
                )
                SheetsHelper.WordType.COMPLEX_SENTENCES -> sheetsHelper.addData(
                    SheetsHelper.WordType.COMPLEX_SENTENCES,
                    Pair(englishWord, koreanWord)
                )
                SheetsHelper.WordType.USEFUL_PHRASES -> sheetsHelper.addData(
                    SheetsHelper.WordType.USEFUL_PHRASES,
                    Pair(englishWord, koreanWord)
                )
                SheetsHelper.WordType.NOUNS -> sheetsHelper.addData(
                    SheetsHelper.WordType.NOUNS,
                    Pair(englishWord, koreanWord)
                )
                SheetsHelper.WordType.POSITIONS -> sheetsHelper.addData(
                    SheetsHelper.WordType.POSITIONS,
                    Pair(englishWord, koreanWord)
                )
            }
        }
    }
}