package com.example.googlesheetskoreanvocabapp.data

import android.content.SharedPreferences
import com.example.googlesheetskoreanvocabapp.common.viewmodel.BaseWordPairViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveResult @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    suspend fun saveTestResults(saveResultCompleteState: BaseWordPairViewModel.SaveResultCompleteState) =
        withContext(coroutineDispatcher) {
            when (saveResultCompleteState.savedResultState.wordType) {
                SheetsHelper.WordType.YUUN.name -> {
                    sharedPreferences.edit()
                        .putString("verbs", Gson().toJson(saveResultCompleteState)).apply()
                }
            }
        }

    suspend fun saveUsedNumbers(usedNumber: List<Int>) =
        withContext(coroutineDispatcher) {
            val currentList = sharedPreferences.getStringSet("numbers", emptySet())?.toMutableSet()
                ?: mutableSetOf()
            val stringSet = usedNumber.map { it.toString() }.toSet()
            if (currentList.isNotEmpty()) {
                currentList.addAll(stringSet)
                sharedPreferences.edit().putStringSet("numbers", currentList).apply()
            } else {
                sharedPreferences.edit().putStringSet("numbers", stringSet).apply()
            }
        }

    suspend fun clearUsedNumbers() =
        withContext(coroutineDispatcher) {
            sharedPreferences.edit().putStringSet("numbers", emptySet()).apply()
        }
}
