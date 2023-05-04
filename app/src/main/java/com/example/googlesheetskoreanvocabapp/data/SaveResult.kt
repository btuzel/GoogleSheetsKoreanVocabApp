package com.example.googlesheetskoreanvocabapp.data

import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveResult @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(resultData: Set<String>) =
        withContext(coroutineDispatcher) {
            val existingData = sharedPreferences.getStringSet("results", setOf()) ?: setOf()
            val newData = existingData.toMutableSet().apply { addAll(resultData) }
            sharedPreferences.edit().putStringSet("results", newData).apply()
        }
}
