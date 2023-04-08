package com.example.googlesheetskoreanvocabapp.verbs

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddVerb @Inject constructor(
    private val sheetsHelper: SheetsHelper,
) {
    suspend operator fun invoke(englishWord: String, koreanWord: String) = withContext(
        Dispatchers.IO
    ) {
        sheetsHelper.addData(SheetsHelper.WordType.VERBS, Pair(englishWord, koreanWord))
    }
}