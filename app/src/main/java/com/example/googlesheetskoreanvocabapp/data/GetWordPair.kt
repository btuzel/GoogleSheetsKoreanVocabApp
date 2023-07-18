package com.example.googlesheetskoreanvocabapp.data

import com.example.googlesheetskoreanvocabapp.common.VerbGroupType
import com.example.googlesheetskoreanvocabapp.common.VerbInstance
import com.example.googlesheetskoreanvocabapp.common.fixStrings
import com.example.googlesheetskoreanvocabapp.common.isOnline
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWordPair @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository,
    private val verbInstance: VerbInstance
) {
    suspend operator fun invoke(wordType: SheetsHelper.WordType): Pair<List<String>, List<String>> =
        withContext(
            Dispatchers.IO
        ) {
            if (isOnline()) {
                when (wordType) {
                    SheetsHelper.WordType.VERBS -> {
                        val verbs =
                            getWordsFromSpreadsheet(SheetsHelper.WordType.VERBS)
                        if(verbInstance.returnGroupType() == VerbGroupType.OLD) {
                            return@withContext Pair(
                                verbs.first.take(80),
                                verbs.second.take(80)
                            )
                        } else if (verbInstance.returnGroupType() == VerbGroupType.ALL) {
                            return@withContext verbs
                        } else if (verbInstance.returnGroupType() == VerbGroupType.NEW){
                            return@withContext Pair(
                                verbs.first.takeLast(verbs.first.size - 80),
                                verbs.second.takeLast(verbs.first.size - 80)
                            )
                        } else {
                            return@withContext verbs
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
