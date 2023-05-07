package com.example.googlesheetskoreanvocabapp.adverbs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.state.DisplayState
import com.example.googlesheetskoreanvocabapp.common.ui.LoadingState
import com.example.googlesheetskoreanvocabapp.common.ui.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayAdverbsScreen(adverbsViewModel: AdverbsViewModel = hiltViewModel()) {
    val allAdverbs by adverbsViewModel.displayAllPairsUiState.collectAsState()
    val wordState = adverbsViewModel.wordState.collectAsState()
    when (val value = allAdverbs) {
        is DisplayState.AllPairs -> ShowPairComposable(
            pairList = value.allPairs,
            wordType = SheetsHelper.WordType.ADVERBS,
            onDelete = adverbsViewModel::deleteWordPair,
            wordState = wordState.value
        )
        DisplayState.Loading -> LoadingState(wordType = SheetsHelper.WordType.ADVERBS)
    }
}