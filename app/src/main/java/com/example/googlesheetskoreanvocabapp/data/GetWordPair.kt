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
                                    verbs.first.take(188),
                                    verbs.second.take(188)
                                )
                            }

                            VerbGroupType.ALL -> {
                                return@withContext verbs
                            }

                            VerbGroupType.NEW -> {
                                val pair = Pair(
                                    verbs.first.subList(188, 216) + verbs.first.subList(
                                        223,
                                        234
                                    ) + verbs.first.subList(287, 295) + verbs.first.subList(
                                        314,
                                        339
                                    ),
                                    verbs.second.subList(188, 216) + verbs.second.subList(
                                        223,
                                        234
                                    ) + verbs.second.subList(287, 295) + verbs.second.subList(
                                        314,
                                        339
                                    )
                                )
                                return@withContext pair
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
                                val toIndex =
                                    verbs.first.indexOf("twelve hours and a half, half word not number") + 1
                                return@withContext Pair(
                                    verbs.first.subList(fromIndex, toIndex),
                                    verbs.second.subList(fromIndex, toIndex)
                                )
                            }

                            VerbGroupType.ANIMAL -> {
                                val fromIndex = verbs.first.indexOf("Dog")
                                val toIndex = verbs.first.indexOf("Gorilla") + 1
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
