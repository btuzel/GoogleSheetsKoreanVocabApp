package com.example.googlesheetskoreanvocabapp.adverbs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.DisplayState
import com.example.googlesheetskoreanvocabapp.common.LoadingState
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayAdverbsScreen(adverbsViewModel: AdverbsViewModel = hiltViewModel()) {
    val allAdverbs by adverbsViewModel.displayAllPairsUiState.collectAsState()
    when (val value = allAdverbs) {
        is DisplayState.AllPairs -> ShowPairComposable(
            value.allPairs,
            SheetsHelper.WordType.ADVERBS,
            adverbsViewModel::deleteWordPair
        )
        DisplayState.Loading -> LoadingState()
    }
}