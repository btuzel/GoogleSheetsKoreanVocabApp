package com.example.googlesheetskoreanvocabapp.common.viewmodel

import com.example.googlesheetskoreanvocabapp.common.state.DisplayState
import com.example.googlesheetskoreanvocabapp.common.state.GetWords
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import kotlinx.coroutines.flow.StateFlow

interface WordPairViewModel {
    val wordType: SheetsHelper.WordType
    val wordPairs: Pair<List<String>, List<String>>
    val displayAllPairsUiState: StateFlow<DisplayState>
    val uiState: StateFlow<GetWords>
    fun addWordPair(englishWord: String, koreanWord: String)
    fun deleteWordPair(englishWord: String, koreanWord: String)
}