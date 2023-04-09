package com.example.googlesheetskoreanvocabapp.adverbs

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.Adverbs
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddAdverbs @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(englishWord: String, koreanWord: String) = withContext(
        Dispatchers.IO
    ) {
        verbRepository.insertAdverbs(
            listOf(
                Adverbs(
                    englishWord = englishWord,
                    koreanWord = koreanWord
                )
            )
        )
        if (isOnline()) {
            sheetsHelper.addData(SheetsHelper.WordType.ADVERBS, Pair(englishWord, koreanWord))
        }
    }
}