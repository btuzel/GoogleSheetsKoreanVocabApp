package com.example.googlesheetskoreanvocabapp.nouns

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNouns @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(): Pair<List<String>, List<String>> = withContext(
        Dispatchers.IO
    ) {
        if (isOnline()) {
            val wordsFromSpreadsheet =
                sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.NOUNS)
            val englishNouns =
                wordsFromSpreadsheet.first!!.map { it.toString() }
            val koreanNouns =
                wordsFromSpreadsheet.second!!.map { it.toString() }
            return@withContext Pair(englishNouns, koreanNouns)
        } else {
            return@withContext verbRepository.getNouns()

        }
    }
}