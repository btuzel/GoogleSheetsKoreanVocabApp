package com.example.googlesheetskoreanvocabapp.data

import androidx.security.crypto.EncryptedSharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveResult @Inject constructor(
    private val encryptedSharedPreferences: EncryptedSharedPreferences,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(resultData: Set<String>) =
        withContext(coroutineDispatcher) {
            val existingData = encryptedSharedPreferences.getStringSet("results", setOf()) ?: setOf()
            val newData = existingData.toMutableSet().apply { addAll(resultData) }
            encryptedSharedPreferences.edit().putStringSet("results", newData).apply()
        }
}
