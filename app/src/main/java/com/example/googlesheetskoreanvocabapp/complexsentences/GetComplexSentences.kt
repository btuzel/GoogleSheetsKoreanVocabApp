package com.example.googlesheetskoreanvocabapp.complexsentences

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetComplexSentences @Inject constructor(
    private val sheetsHelper: SheetsHelper,
) {
    suspend operator fun invoke(): Pair<List<String>, List<String>> = withContext(
        Dispatchers.IO
    ) {
        val asd = sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.COMPLEX_SENTENCES)
        val englishAdverbs = asd.first!!.map { it.toString() }
        val koreanAdverbs = asd.second!!.map { it.toString() }
        return@withContext Pair(englishAdverbs, koreanAdverbs)
    }

}