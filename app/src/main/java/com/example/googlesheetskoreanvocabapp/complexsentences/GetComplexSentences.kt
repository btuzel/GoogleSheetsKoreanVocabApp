package com.example.googlesheetskoreanvocabapp.complexsentences

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetComplexSentences @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(): Pair<List<String>, List<String>> = withContext(
        Dispatchers.IO
    ) {
        if(isOnline()) {
            val wordsFromSpreadsheet =
                sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.COMPLEX_SENTENCES)
            val englishSentences =
                wordsFromSpreadsheet.first!!.map { it.toString() }
            val koreanSentences =
                wordsFromSpreadsheet.second!!.map { it.toString() }
            return@withContext Pair(englishSentences, koreanSentences)
        } else {
            return@withContext verbRepository.getSentences()
        }
    }

}