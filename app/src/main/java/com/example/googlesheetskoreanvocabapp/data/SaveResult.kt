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
            sharedPreferences.edit().putStringSet(
                "results",
                (sharedPreferences.getStringSet("results", setOf()) ?: setOf()).toMutableSet()
                    .apply<MutableSet<String>> { addAll(resultData) }).apply()
        }
}
