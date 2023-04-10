package com.example.googlesheetskoreanvocabapp.nouns

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.db.Adverbs
import com.example.googlesheetskoreanvocabapp.db.Nouns
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteNouns @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(englishWord: String, koreanWord: String) = withContext(
        Dispatchers.IO
    ) {
        verbRepository.deleteNouns(
            Nouns(
                englishWord = englishWord,
                koreanWord = koreanWord
            )
        )
        if (isOnline()) {
            sheetsHelper.deleteData(SheetsHelper.WordType.NOUNS, Pair(englishWord, koreanWord))
        }
    }
}