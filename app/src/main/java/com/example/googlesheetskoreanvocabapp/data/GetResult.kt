package com.example.googlesheetskoreanvocabapp.data

import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetResult @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Set<String>? =
        withContext(coroutineDispatcher) {
            sharedPreferences.getStringSet("results", null)
        }
}
