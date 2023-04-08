package com.example.googlesheetskoreanvocabapp.usefulphrases

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddPhrases @Inject constructor(
    private val sheetsHelper: SheetsHelper,
) {
    suspend operator fun invoke(englishWord: String, koreanWord: String) = withContext(
        Dispatchers.IO
    ) {
        sheetsHelper.addData(SheetsHelper.WordType.USEFUL_PHRASES, Pair(englishWord, koreanWord))
    }
}