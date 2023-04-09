package com.example.googlesheetskoreanvocabapp.adverbs

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAdverbs @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(): Pair<List<String>, List<String>> = withContext(
        Dispatchers.IO
    ) {
        if (isOnline()) {
            val wordsFromSpreadsheet =
                sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.ADVERBS)
            val englishAdverbs =
                wordsFromSpreadsheet.first!!.map { it.toString() }
            val koreanAdverbs =
                wordsFromSpreadsheet.second!!.map { it.toString() }
            return@withContext Pair(englishAdverbs, koreanAdverbs)
        } else {
            return@withContext verbRepository.getAdverbs()
        }
    }
}

