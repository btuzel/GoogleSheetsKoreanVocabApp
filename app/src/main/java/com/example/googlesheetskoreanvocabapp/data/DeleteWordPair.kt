package com.example.googlesheetskoreanvocabapp.data

import com.example.googlesheetskoreanvocabapp.common.isOnline
import com.example.googlesheetskoreanvocabapp.db.Hyungseok
import com.example.googlesheetskoreanvocabapp.db.MainRepositoryDatabase
import com.example.googlesheetskoreanvocabapp.db.OldWords
import com.example.googlesheetskoreanvocabapp.db.Repeatables
import com.example.googlesheetskoreanvocabapp.db.Yuun
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteWordPair @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val mainRepositoryDatabase: MainRepositoryDatabase
) {
    suspend operator fun invoke(
        englishWord: String,
        koreanWord: String,
        wordType: SheetsHelper.WordType
    ) = withContext(
        Dispatchers.IO
    ) {
        when (wordType) {
            SheetsHelper.WordType.YUUN -> {
                mainRepositoryDatabase.deleteYuuns(
                    Yuun(
                        englishWord = englishWord,
                        koreanWord = koreanWord
                    )
                )
            }

            SheetsHelper.WordType.REPEATABLES -> {
                mainRepositoryDatabase.deleteRepeatables(
                    Repeatables(
                        englishWord = englishWord,
                        koreanWord = koreanWord
                    )
                )
            }

            SheetsHelper.WordType.OLDWORDS -> {
                mainRepositoryDatabase.deleteOldWords(
                    OldWords(
                        englishWord = englishWord,
                        koreanWord = koreanWord
                    )
                )
            }

            SheetsHelper.WordType.HYUNGSEOK -> {
                mainRepositoryDatabase.deleteHyungseoks(
                    Hyungseok(
                        englishWord = englishWord,
                        koreanWord = koreanWord
                    )
                )
            }
        }
        if (isOnline()) {
            when (wordType) {
                SheetsHelper.WordType.YUUN -> {
                    return@withContext sheetsHelper.deleteData(
                        SheetsHelper.WordType.YUUN,
                        Pair(englishWord, koreanWord)
                    )
                }

                SheetsHelper.WordType.REPEATABLES -> {
                    return@withContext sheetsHelper.deleteData(
                        SheetsHelper.WordType.REPEATABLES,
                        Pair(englishWord, koreanWord)
                    )
                }

                SheetsHelper.WordType.OLDWORDS -> {
                    return@withContext sheetsHelper.deleteData(
                        SheetsHelper.WordType.OLDWORDS,
                        Pair(englishWord, koreanWord)
                    )
                }

                SheetsHelper.WordType.HYUNGSEOK -> {
                    return@withContext sheetsHelper.deleteData(
                        SheetsHelper.WordType.HYUNGSEOK,
                        Pair(englishWord, koreanWord)
                    )
                }
            }
        } else {
            return@withContext false
        }
    }
}