package com.example.googlesheetskoreanvocabapp.usefulphrases

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPhrases @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(): Pair<List<String>, List<String>> = withContext(
        Dispatchers.IO
    ) {
        if(isOnline()) {
            val wordsFromSpreadsheet =
                sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.USEFUL_PHRASES)
            val englishPhrases =
                wordsFromSpreadsheet.first!!.map { it.toString().replace("[","").replace("]","") }
            val koreanPhrases =
                wordsFromSpreadsheet.second!!.map { it.toString().replace("[","").replace("]","") }
            return@withContext Pair(englishPhrases, koreanPhrases)
        } else {
            return@withContext verbRepository.getPhrases()
        }
    }

}