package com.example.googlesheetskoreanvocabapp.data

import android.content.SharedPreferences
import com.example.googlesheetskoreanvocabapp.common.viewmodel.BaseWordPairViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveResult @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(saveResultCompleteState: BaseWordPairViewModel.SaveResultCompleteState) =
        withContext(coroutineDispatcher) {
            when (saveResultCompleteState.savedResultState.wordType) {
                SheetsHelper.WordType.POSITIONS.name -> {
                    sharedPreferences.edit()
                        .putString("positions", Gson().toJson(saveResultCompleteState)).apply()
                }

                SheetsHelper.WordType.VERBS.name -> {
                    sharedPreferences.edit()
                        .putString("verbs", Gson().toJson(saveResultCompleteState)).apply()
                }

                SheetsHelper.WordType.ADVERBS.name -> {
                    sharedPreferences.edit()
                        .putString("adverbs", Gson().toJson(saveResultCompleteState)).apply()
                }

                SheetsHelper.WordType.USEFUL_PHRASES.name -> {
                    sharedPreferences.edit()
                        .putString("phrases", Gson().toJson(saveResultCompleteState)).apply()
                }

                SheetsHelper.WordType.NOUNS.name -> {
                    sharedPreferences.edit()
                        .putString("nouns", Gson().toJson(saveResultCompleteState)).apply()
                }

                SheetsHelper.WordType.COMPLEX_SENTENCES.name -> {
                    sharedPreferences.edit()
                        .putString("sentences", Gson().toJson(saveResultCompleteState)).apply()
                }
            }
        }
}
