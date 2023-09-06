package com.example.googlesheetskoreanvocabapp.data

import android.util.Log
import com.example.googlesheetskoreanvocabapp.GlobalClass
import com.example.googlesheetskoreanvocabapp.common.fixStrings
import com.example.googlesheetskoreanvocabapp.common.isOnline
import com.example.googlesheetskoreanvocabapp.db.MainRepositoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class GetWordPair @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val mainRepositoryDatabase: MainRepositoryDatabase,
    private val getResult: GetResult,
    private val saveResult: SaveResult
) {
    suspend operator fun invoke(
        wordType: SheetsHelper.WordType,
        justDisplay: Boolean = false
    ): Pair<List<String>, List<String>> =
        withContext(
            Dispatchers.IO
        ) {
            val YUUN =
                getWordsFromSpreadsheet(SheetsHelper.WordType.YUUN)
            val HYUNGSEOK =
                getWordsFromSpreadsheet(SheetsHelper.WordType.HYUNGSEOK)
            val REPEATABLES =
                getWordsFromSpreadsheet(SheetsHelper.WordType.REPEATABLES)
            val OLDWORDS =
                getWordsFromSpreadsheet(SheetsHelper.WordType.OLDWORDS)
            if (isOnline()) {
                when (wordType) {
                    SheetsHelper.WordType.YUUN -> {
                        if(GlobalClass.doLast50) {
                            GlobalClass.doLast50 = false
                            return@withContext Pair(
                                YUUN.first.takeLast(50),
                                YUUN.second.takeLast(50)
                            )
                        }
                        if(justDisplay) {
                            return@withContext YUUN
                        }

                        val numbersToNotUse = getResult.getListToNotUse()
                        Log.d("numbersToNotUSe", numbersToNotUse.toString())
                        val fromIndex = YUUN.first.indexOf("china")
                        val toIndex = YUUN.first.size
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
                        val sublist1 = listToUse.map { index -> YUUN.first[index] }
                        val sublist2 = listToUse.map { index -> YUUN.second[index] }
                        return@withContext Pair(sublist1, sublist2)
                    }

                    SheetsHelper.WordType.REPEATABLES -> {
                        return@withContext REPEATABLES
                    }

                    SheetsHelper.WordType.OLDWORDS -> {
                        return@withContext OLDWORDS
                    }

                    SheetsHelper.WordType.HYUNGSEOK -> {
                        return@withContext HYUNGSEOK
                    }
                }
            } else {

                when (wordType) {
                    SheetsHelper.WordType.YUUN -> return@withContext mainRepositoryDatabase.getYuuns()
                    SheetsHelper.WordType.REPEATABLES -> return@withContext mainRepositoryDatabase.getRepeatables()
                    SheetsHelper.WordType.OLDWORDS -> return@withContext mainRepositoryDatabase.getOldWords()
                    SheetsHelper.WordType.HYUNGSEOK -> return@withContext mainRepositoryDatabase.getHyungseoks()
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
        if (count > toIndex - fromIndex + 1 - exclude.size) {
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
