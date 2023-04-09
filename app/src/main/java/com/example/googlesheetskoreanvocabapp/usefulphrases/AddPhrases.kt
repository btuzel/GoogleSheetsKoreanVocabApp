package com.example.googlesheetskoreanvocabapp.usefulphrases

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.Phrases
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddPhrases @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(englishWord: String, koreanWord: String) = withContext(
        Dispatchers.IO
    ) {
        verbRepository.insertPhrases(
            listOf(
                Phrases(
                    englishWord = englishWord,
                    koreanWord = koreanWord
                )
            )
        )
        if (isOnline()) {
            sheetsHelper.addData(
                SheetsHelper.WordType.USEFUL_PHRASES,
                Pair(englishWord, koreanWord)
            )
        }
    }
}