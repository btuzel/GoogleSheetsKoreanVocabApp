package com.example.googlesheetskoreanvocabapp.data

import com.example.googlesheetskoreanvocabapp.common.isOnline
import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.db.Verbs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddWordPair @Inject constructor(
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
                verbRepository.insertVerbs(
                    listOf(
                        Verbs(
                            englishWord = englishWord,
                            koreanWord = koreanWord
                        )
                    )
                )
            }
        }
        if (isOnline()) {
            when (wordType) {
                SheetsHelper.WordType.VERBS -> {
                    return@withContext sheetsHelper.addData(
                        SheetsHelper.WordType.VERBS,
                        Pair<String, String>(englishWord, koreanWord)
                    )
                }
            }
        } else {
            return@withContext false
        }
    }
}