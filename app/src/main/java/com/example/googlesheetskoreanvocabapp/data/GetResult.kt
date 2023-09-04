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
    suspend fun getTestResults(): List<BaseWordPairViewModel.SaveResultCompleteState> =
        withContext(coroutineDispatcher) {
            val x = BaseWordPairViewModel.SaveResultCompleteState(
                SaveResultState("", ""),
                "",
                "",
                "",
                listOf()
            )
            val sharedPreferencesKeys = listOf(
                "verbs"
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

    suspend fun getListToNotUse(): List<Int> =
        withContext(coroutineDispatcher) {
            val storedSet = sharedPreferences.getStringSet("numbers", emptySet())
            return@withContext storedSet?.map<String?, Int> { it!!.toInt() } ?: emptyList<Int>()
        }

}
