package com.example.googlesheetskoreanvocabapp.data

import com.example.googlesheetskoreanvocabapp.db.Adverbs
import com.example.googlesheetskoreanvocabapp.db.Nouns
import com.example.googlesheetskoreanvocabapp.db.Phrases
import com.example.googlesheetskoreanvocabapp.db.Positions
import com.example.googlesheetskoreanvocabapp.db.Sentences
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.db.Verbs
import com.example.googlesheetskoreanvocabapp.common.isOnline
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
                SheetsHelper.WordType.VERBS -> {
                    return@withContext sheetsHelper.addData(
                        SheetsHelper.WordType.VERBS,
                        Pair<String, String>(englishWord, koreanWord)
                    )
                }

                SheetsHelper.WordType.ADVERBS -> {
                    return@withContext sheetsHelper.addData(
                        SheetsHelper.WordType.ADVERBS,
                        Pair<String, String>(englishWord, koreanWord)
                    )
                }

                SheetsHelper.WordType.COMPLEX_SENTENCES -> {
                    return@withContext sheetsHelper.addData(
                        SheetsHelper.WordType.COMPLEX_SENTENCES,
                        Pair<String, String>(englishWord, koreanWord)
                    )
                }

                SheetsHelper.WordType.USEFUL_PHRASES -> {
                    return@withContext sheetsHelper.addData(
                        SheetsHelper.WordType.USEFUL_PHRASES,
                        Pair<String, String>(englishWord, koreanWord)
                    )
                }

                SheetsHelper.WordType.NOUNS -> {
                    return@withContext sheetsHelper.addData(
                        SheetsHelper.WordType.NOUNS,
                        Pair<String, String>(englishWord, koreanWord)
                    )
                }

                SheetsHelper.WordType.POSITIONS -> {
                    return@withContext sheetsHelper.addData(
                        SheetsHelper.WordType.POSITIONS,
                        Pair<String, String>(englishWord, koreanWord)
                    )
                }
            }
        } else {
            return@withContext false
        }
    }
}