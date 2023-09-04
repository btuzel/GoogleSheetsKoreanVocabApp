package com.example.googlesheetskoreanvocabapp.data

import android.util.Log
import com.example.googlesheetskoreanvocabapp.GlobalClass
import com.example.googlesheetskoreanvocabapp.common.VerbGroupType
import com.example.googlesheetskoreanvocabapp.common.fixStrings
import com.example.googlesheetskoreanvocabapp.common.isOnline
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class GetWordPair @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository,
    private val getResult: GetResult,
    private val saveResult: SaveResult
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

                            VerbGroupType.HYUNGSEOK -> {
                                val subList =
                                    verbs.first.subList(188, 216) // trip to kac kisi burada
                                val subList1 =
                                    verbs.first.subList(223, 234) // foreigner to iwanttoswim
                                val subList2 =
                                    verbs.first.subList(287, 295) // to teach to okadindoktor
                                val subList3 = verbs.first.subList(
                                    332,
                                    357
                                ) // kackardesinvar to LAST ITEM
                                val subListKor = verbs.second.subList(188, 216)
                                val subList1Kor = verbs.second.subList(223, 234)
                                val subList2Kor = verbs.second.subList(287, 295)
                                val subList3Kor = verbs.second.subList(332, 357)
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
                                val numbersToNotUse = getResult.getListToNotUse()
                                Log.d("numbersToNotUSe", numbersToNotUse.toString())
                                val fromIndex = verbs.first.indexOf("china")
                                val toIndex = verbs.first.size
                                val listToUse: List<Int>
                                if (numbersToNotUse.size >= toIndex - fromIndex) {
                                    saveResult.clearUsedNumbers()
                                    listToUse = generateRandomIntegers(
                                        fromIndex,
                                        toIndex,
                                        (toIndex - fromIndex) / 3,
                                    )
                                    Log.d("listToUse", listToUse.toString())
                                    saveResult.saveUsedNumbers(listToUse)
                                } else {
                                    listToUse = generateRandomIntegers(
                                        fromIndex,
                                        toIndex,
                                        (toIndex - fromIndex) / 3,
                                        numbersToNotUse
                                    )
                                    if (numbersToNotUse.isNotEmpty()) {
                                        Log.d("numbersToNotUSe", numbersToNotUse.toString())
                                        Log.d("listToUse", listToUse.toString())
                                        Log.d("listToUsesize", listToUse.size.toString())
                                        Log.d("numbersToNotUSe", numbersToNotUse.size.toString())

                                        saveResult.saveUsedNumbers(listToUse + numbersToNotUse)
                                    } else {
                                        Log.d("listToUse", listToUse.toString())
                                        Log.d("listToUsesize", listToUse.size.toString())
                                        saveResult.saveUsedNumbers(listToUse)
                                    }
                                }
                                val sublist1 = listToUse.map { index -> verbs.first[index] }
                                val sublist2 = listToUse.map { index -> verbs.second[index] }
                                return@withContext Pair(sublist1, sublist2)
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

                            VerbGroupType.LASTXNUMBERS -> {

                                val subList =
                                    verbs.first.subList(
                                        GlobalClass.globalProperty.first - 1,
                                        GlobalClass.globalProperty.second
                                    )
                                val subListKor = verbs.second.subList(
                                    GlobalClass.globalProperty.first - 1,
                                    GlobalClass.globalProperty.second
                                )
                                return@withContext Pair<List<String>, List<String>>(
                                    subList,
                                    subListKor
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


    fun generateRandomIntegers(
        fromIndex: Int,
        toIndex: Int,
        count: Int,
        exclude: List<Int> = emptyList()
    ): List<Int> {
        if(count > toIndex - fromIndex + 1 - exclude.size) {
            val randomIntegers = mutableSetOf<Int>()

            while (randomIntegers.size < count) {
                val randomValue = Random.nextInt(fromIndex, toIndex)
                randomIntegers.add(randomValue)
            }

            return randomIntegers.toList()
        }
        if (exclude.isNotEmpty()) {
            val excludedSet = exclude.toSet()
            val result = mutableSetOf<Int>()

            while (result.size < count) {
                val randomValue = (fromIndex until toIndex).random()
                if (randomValue !in excludedSet) {
                    result.add(randomValue)
                }
            }
            return result.toList()

        } else {
            val randomIntegers = mutableSetOf<Int>()

            while (randomIntegers.size < count) {
                val randomValue = Random.nextInt(fromIndex, toIndex)
                randomIntegers.add(randomValue)
            }

            return randomIntegers.toList()
        }
    }
}
