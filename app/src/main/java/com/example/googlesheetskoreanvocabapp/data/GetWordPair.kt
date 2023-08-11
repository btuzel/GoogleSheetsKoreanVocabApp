package com.example.googlesheetskoreanvocabapp.data

import com.example.googlesheetskoreanvocabapp.common.VerbGroupType
import com.example.googlesheetskoreanvocabapp.common.fixStrings
import com.example.googlesheetskoreanvocabapp.common.isOnline
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWordPair @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository,
    //private val verbInstance: VerbInstance
) {
    suspend operator fun invoke(
        wordType: SheetsHelper.WordType,
        verbGroupType: VerbGroupType = VerbGroupType.ALL
    ): Pair<List<String>, List<String>> =
        withContext(
            Dispatchers.IO
        ) {
            if (isOnline()) {
                when (wordType) {
                    SheetsHelper.WordType.VERBS -> {
                        val verbs =
                            getWordsFromSpreadsheet(SheetsHelper.WordType.VERBS)
                        when (verbGroupType) {
                            VerbGroupType.OLD -> {
                                return@withContext Pair(
                                    verbs.first.take(130),
                                    verbs.second.take(130)
                                )
                            }

                            VerbGroupType.ALL -> {
                                return@withContext verbs
                            }

                            VerbGroupType.NEW -> {
                                return@withContext Pair(
                                    verbs.first.takeLast(verbs.first.size - 130),
                                    verbs.second.takeLast(verbs.first.size - 130)
                                )
                            }

                            VerbGroupType.COLORS -> {
                                return@withContext Pair(
                                    verbs.first.subList(247, 258),
                                    verbs.second.subList(247, 258)
                                )
                            }

                            VerbGroupType.UPDOWNLEFTRIGHT -> {
                                return@withContext Pair(
                                    verbs.first.subList(235,247),
                                    verbs.second.subList(235,247)
                                )
                            }

                            VerbGroupType.WEEKDAYS -> {
                                return@withContext Pair(
                                    verbs.first.subList(217,224),
                                    verbs.second.subList(217,224)
                                )
                            }

                            VerbGroupType.TIME ->
                                return@withContext Pair(
                                verbs.first.subList(258,288),
                                verbs.second.subList(258,288)
                            )
                            else -> {
                                return@withContext verbs
                            }
                        }
                    }
                }
            } else {
                when (wordType) {
                    SheetsHelper.WordType.VERBS -> return@withContext verbRepository.getVerbs()
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
