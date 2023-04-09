package com.example.googlesheetskoreanvocabapp.adverbs

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAdverbs @Inject constructor(
    private val sheetsHelper: SheetsHelper,
) {
    suspend operator fun invoke(): Pair<List<String>, List<String>> = withContext(
        Dispatchers.IO
    ) {
        val englishAdverbs =
            sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.ADVERBS).first!!.map { it.toString() }
        val koreanAdverbs =
            sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.ADVERBS).second!!.map { it.toString() }
        return@withContext Pair(englishAdverbs, koreanAdverbs)
    }

}