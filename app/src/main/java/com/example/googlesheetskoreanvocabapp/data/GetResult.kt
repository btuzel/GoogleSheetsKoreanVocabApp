package com.example.googlesheetskoreanvocabapp.data

import android.content.SharedPreferences
import com.example.googlesheetskoreanvocabapp.common.ui.SaveResultState
import com.example.googlesheetskoreanvocabapp.common.viewmodel.BaseWordPairViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetResult @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): List<BaseWordPairViewModel.SaveResultCompleteState> =
        withContext(coroutineDispatcher) {
            val x = BaseWordPairViewModel.SaveResultCompleteState(
                SaveResultState("", ""),
                "",
                "",
                "",
                listOf()
            )
            val sharedPreferencesKeys = listOf(
                "nouns", "verbs", "adverbs", "phrases", "sentences", "positions"
            )
            val gson = Gson()
            val resultList = mutableListOf<BaseWordPairViewModel.SaveResultCompleteState>()

            for (key in sharedPreferencesKeys) {
                val jsonString = sharedPreferences.getString(key, "")
                val saveResultCompleteState = gson.fromJson(
                    jsonString,
                    BaseWordPairViewModel.SaveResultCompleteState::class.java
                ) ?: x
                resultList.add(saveResultCompleteState)
            }

            return@withContext resultList
        }
}
