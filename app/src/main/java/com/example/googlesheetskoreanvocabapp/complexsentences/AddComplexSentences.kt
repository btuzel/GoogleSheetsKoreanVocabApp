package com.example.googlesheetskoreanvocabapp.complexsentences

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.Sentences
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddComplexSentences @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository

) {
    suspend operator fun invoke(englishWord: String, koreanWord: String) = withContext(
        Dispatchers.IO
    ) {
        verbRepository.insertSentences(
            listOf(
                Sentences(
                    englishWord = englishWord,
                    koreanWord = koreanWord
                )
            )
        )
        if (isOnline()) {
            sheetsHelper.addData(
                SheetsHelper.WordType.COMPLEX_SENTENCES,
                Pair(englishWord, koreanWord)
            )
        }
    }
}