package com.example.googlesheetskoreanvocabapp.data

import com.example.googlesheetskoreanvocabapp.common.isOnline
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.db.Verbs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteWordPair @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(
        englishWord: String,
        koreanWord: String,
        wordType: SheetsHelper.WordType
    ) = withContext(
        Dispatchers.IO
    ) {
        when (wordType) {
            SheetsHelper.WordType.VERBS -> {
                verbRepository.deleteVerb(
                    Verbs(
                        englishWord = englishWord,
                        koreanWord = koreanWord
                    )
                )
            }
        }
        if (isOnline()) {
            when (wordType) {
                SheetsHelper.WordType.VERBS -> {
                    return@withContext sheetsHelper.deleteData(
                        SheetsHelper.WordType.VERBS,
                        Pair(englishWord, koreanWord)
                    )
                }
            }
        } else {
            return@withContext false
        }
    }
}