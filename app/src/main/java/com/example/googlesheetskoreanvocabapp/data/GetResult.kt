package com.example.googlesheetskoreanvocabapp.data

import androidx.security.crypto.EncryptedSharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetResult @Inject constructor(
    private val encryptedSharedPreferences: EncryptedSharedPreferences,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Set<String>? =
        withContext(coroutineDispatcher) {
            encryptedSharedPreferences.getStringSet("results", null)
        }
}
