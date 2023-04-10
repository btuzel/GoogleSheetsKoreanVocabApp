package com.example.googlesheetskoreanvocabapp.usefulphrases

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.*
import com.example.googlesheetskoreanvocabapp.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeletePhrases @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(englishWord: String, koreanWord: String) = withContext(
        Dispatchers.IO
    ) {
        verbRepository.deletePhrases(
            Phrases(
                englishWord = englishWord,
                koreanWord = koreanWord
            )
        )
        if (isOnline()) {
            sheetsHelper.deleteData(SheetsHelper.WordType.USEFUL_PHRASES, Pair(englishWord, koreanWord))
        }
    }
}