package com.example.googlesheetskoreanvocabapp.data

import com.example.googlesheetskoreanvocabapp.common.fixStrings
import com.example.googlesheetskoreanvocabapp.common.isOnline
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWordPair @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(
        wordType: SheetsHelper.WordType,
        offset: Int,
        limit: Int
    ): Pair<List<String>, List<String>> =
        withContext(
            Dispatchers.IO
        ) {
            if (isOnline()) {
                when (wordType) {
                    SheetsHelper.WordType.VERBS -> {
                        val verbs =
                            getWordsFromSpreadsheet(SheetsHelper.WordType.VERBS)

                        return@withContext Pair(
                            verbs.first.subList(offset, if(verbs.first.size < limit) verbs.first.size else limit), //0,80    80,2000     0,2000
                            verbs.second.subList(offset, verbs.second.size)
                        )
                    }

                    SheetsHelper.WordType.ADVERBS -> {
                        return@withContext getWordsFromSpreadsheet(SheetsHelper.WordType.ADVERBS)
                    }

                    SheetsHelper.WordType.COMPLEX_SENTENCES -> {
                        return@withContext getWordsFromSpreadsheet(SheetsHelper.WordType.COMPLEX_SENTENCES)
                    }

                    SheetsHelper.WordType.USEFUL_PHRASES -> {
                        return@withContext getWordsFromSpreadsheet(SheetsHelper.WordType.USEFUL_PHRASES)
                    }

                    SheetsHelper.WordType.NOUNS -> {
                        return@withContext getWordsFromSpreadsheet(SheetsHelper.WordType.NOUNS)
                    }

                    SheetsHelper.WordType.POSITIONS -> {
                        return@withContext getWordsFromSpreadsheet(SheetsHelper.WordType.POSITIONS)
                    }
                }
            } else {
                when (wordType) {
                    SheetsHelper.WordType.VERBS -> return@withContext verbRepository.getVerbs()
                    SheetsHelper.WordType.ADVERBS -> return@withContext verbRepository.getAdverbs()
                    SheetsHelper.WordType.COMPLEX_SENTENCES -> return@withContext verbRepository.getSentences()
                    SheetsHelper.WordType.USEFUL_PHRASES -> return@withContext verbRepository.getPhrases()
                    SheetsHelper.WordType.NOUNS -> return@withContext verbRepository.getNouns()
                    SheetsHelper.WordType.POSITIONS -> return@withContext verbRepository.getPositions()
                }
            }
        }

    private suspend fun getWordsFromSpreadsheet(wordType: SheetsHelper.WordType): Pair<List<String>, List<String>> {
        val wordsFromSpreadsheet = sheetsHelper.getWordsFromSpreadsheet(wordType = wordType)
        return Pair(
            wordsFromSpreadsheet.first!!.map {
                it.toString().fixStrings()
            },
            wordsFromSpreadsheet.second!!.map {
                it.toString().fixStrings()
            }
        )
    }
}
