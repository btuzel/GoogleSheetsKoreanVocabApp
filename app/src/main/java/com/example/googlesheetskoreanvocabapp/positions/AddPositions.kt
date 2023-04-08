package com.example.googlesheetskoreanvocabapp.positions

import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddPositions @Inject constructor(
    private val sheetsHelper: SheetsHelper,
) {
    suspend operator fun invoke(englishWord: String, koreanWord: String) = withContext(
        Dispatchers.IO
    ) {
        sheetsHelper.addData(SheetsHelper.WordType.POSITIONS, Pair(englishWord, koreanWord))
    }
}