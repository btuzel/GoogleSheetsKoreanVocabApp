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
            val nouns = Gson().fromJson(
                sharedPreferences.getString("nouns", ""),
                BaseWordPairViewModel.SaveResultCompleteState::class.java
            ) ?: x
            val verbs = Gson().fromJson(
                sharedPreferences.getString("verbs", ""),
                BaseWordPairViewModel.SaveResultCompleteState::class.java
            ) ?: x
            val adverbs = Gson().fromJson(
                sharedPreferences.getString("adverbs", ""),
                BaseWordPairViewModel.SaveResultCompleteState::class.java
            ) ?: x
            val phrases = Gson().fromJson(
                sharedPreferences.getString("phrases", ""),
                BaseWordPairViewModel.SaveResultCompleteState::class.java
            ) ?: x
            val sentences = Gson().fromJson(
                sharedPreferences.getString("sentences", ""),
                BaseWordPairViewModel.SaveResultCompleteState::class.java
            ) ?: x
            val positions = Gson().fromJson(
                sharedPreferences.getString("positions", ""),
                BaseWordPairViewModel.SaveResultCompleteState::class.java
            ) ?: x
            return@withContext listOf<BaseWordPairViewModel.SaveResultCompleteState>(
                nouns, verbs, adverbs, positions, phrases, sentences
            )
        }
}
