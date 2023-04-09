package com.example.googlesheetskoreanvocabapp.verbs

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetVerbs @Inject constructor(
    private val sheetsHelper: SheetsHelper,
) {
    suspend operator fun invoke(): Pair<List<String>, List<String>> = withContext(
        Dispatchers.IO
    ) {
        sheetsHelper.syncDataForVerbs()
        val asd = sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.VERBS)
        val englishVerbs = asd.first!!.map { it.toString() }
        val koreanVerbs = asd.second!!.map { it.toString() }
        return@withContext Pair(englishVerbs, koreanVerbs)
    }
}