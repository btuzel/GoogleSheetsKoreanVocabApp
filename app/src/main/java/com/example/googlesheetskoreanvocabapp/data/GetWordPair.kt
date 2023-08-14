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
                                val fromIndex = verbs.first.indexOf("Red")
                                val toIndex = verbs.first.indexOf("White") + 1
                                return@withContext Pair(
                                    verbs.first.subList(fromIndex, toIndex),
                                    verbs.second.subList(fromIndex, toIndex)
                                )
                            }

                            VerbGroupType.UPDOWNLEFTRIGHT -> {
                                val fromIndex = verbs.first.indexOf("yan")
                                val toIndex = verbs.first.indexOf("binanin arkasi") + 1
                                return@withContext Pair(
                                    verbs.first.subList(fromIndex, toIndex),
                                    verbs.second.subList(fromIndex, toIndex)
                                )
                            }

                            VerbGroupType.WEEKDAYS -> {
                                val fromIndex = verbs.first.indexOf("monday")
                                val toIndex = verbs.first.indexOf("sunday") + 1
                                return@withContext Pair(
                                    verbs.first.subList(fromIndex, toIndex),
                                    verbs.second.subList(fromIndex, toIndex)
                                )
                            }

                            VerbGroupType.TIME -> {
                                val fromIndex = verbs.first.indexOf("1 o clock")
                                val toIndex = verbs.first.indexOf("twelve hours and a half, half word not number") + 1
                                return@withContext Pair(
                                    verbs.first.subList(fromIndex, toIndex),
                                    verbs.second.subList(fromIndex, toIndex)
                                )
                            }

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
