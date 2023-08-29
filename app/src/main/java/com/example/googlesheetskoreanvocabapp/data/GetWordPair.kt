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
                                val subList = verbs.first.subList(188, 216) // trip to kac kisi burada
                                val subList1 = verbs.first.subList(223, 234) // foreigner to iwanttoswim
                                val subList2 = verbs.first.subList(287, 295) // to teach to okadindoktor
                                val subList3 = verbs.first.subList(314, verbs.first.size) // kackardesinvar to LAST ITEM
                                val subListKor = verbs.second.subList(188, 216)
                                val subList1Kor = verbs.second.subList(223, 234)
                                val subList2Kor = verbs.second.subList(287, 295)
                                val subList3Kor = verbs.second.subList(314, verbs.second.size)
                                return@withContext Pair<List<String>, List<String>>(
                                    subList + subList1 + subList2 + subList3,
                                    subListKor + subList1Kor + subList2Kor + subList3Kor
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

                            VerbGroupType.YUUN -> {
                                val fromIndex = verbs.first.indexOf("china")
                                val toIndex = verbs.first.size
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

                            VerbGroupType.BODYPARTS -> {
                                val fromIndex = verbs.first.indexOf("Head")
                                val toIndex = verbs.first.indexOf("Genitals") + 1
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
