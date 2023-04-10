package com.example.googlesheetskoreanvocabapp.adverbs

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.Adverbs
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteAdverb @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(englishWord: String, koreanWord: String) = withContext(
        Dispatchers.IO
    ) {
        verbRepository.deleteAdverbs(
            Adverbs(
                englishWord = englishWord,
                koreanWord = koreanWord
            )
        )
        if (isOnline()) {
            sheetsHelper.deleteData(SheetsHelper.WordType.ADVERBS, Pair(englishWord, koreanWord))
        }
    }
}