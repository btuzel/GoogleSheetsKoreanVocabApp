package com.example.googlesheetskoreanvocabapp.complexsentences

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.Sentences
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteComplexSentences @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(englishWord: String, koreanWord: String) = withContext(
        Dispatchers.IO
    ) {
        verbRepository.deleteSentence(
            Sentences(
                englishWord = englishWord,
                koreanWord = koreanWord
            )
        )
        if (isOnline()) {
            sheetsHelper.deleteData(SheetsHelper.WordType.COMPLEX_SENTENCES, Pair(englishWord, koreanWord))
        }
    }
}