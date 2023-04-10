package com.example.googlesheetskoreanvocabapp.verbs

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetVerbs @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(): Pair<List<String>, List<String>> = withContext(
        Dispatchers.IO
    ) {
        if(isOnline()) {
            val wordsFromSpreadsheet =
                sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.VERBS)
            val englishVerbs =
                wordsFromSpreadsheet.first!!.map { it.toString().replace("[","").replace("]","") }
            val koreanVerbs =
                wordsFromSpreadsheet.second!!.map { it.toString().replace("[","").replace("]","") }
            return@withContext Pair(englishVerbs, koreanVerbs)
        } else {
            return@withContext verbRepository.getVerbs()
        }
    }
}