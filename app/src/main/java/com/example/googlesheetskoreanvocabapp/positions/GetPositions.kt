package com.example.googlesheetskoreanvocabapp.positions

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPositions @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(): Pair<List<String>, List<String>> = withContext(
        Dispatchers.IO
    ){
        if(isOnline()) {
            val wordsFromSpreadsheet =
                sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.POSITIONS)
            val englishPositions =
                wordsFromSpreadsheet.first!!.map { it.toString() }
            val koreanPositions =
                wordsFromSpreadsheet.second!!.map { it.toString() }
            return@withContext Pair(englishPositions, koreanPositions)
        } else {
            return@withContext verbRepository.getPositions()
        }
    }

}