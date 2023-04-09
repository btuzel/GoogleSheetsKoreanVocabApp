package com.example.googlesheetskoreanvocabapp.positions

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.Nouns
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddPositions @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository

) {
    suspend operator fun invoke(englishWord: String, koreanWord: String) = withContext(
        Dispatchers.IO
    ) {
        verbRepository.insertNouns(
            listOf(
                Nouns(
                    englishWord = englishWord,
                    koreanWord = koreanWord
                )
            )
        )
        if (isOnline()) {
            sheetsHelper.addData(SheetsHelper.WordType.NOUNS, Pair(englishWord, koreanWord))
        }
    }
}