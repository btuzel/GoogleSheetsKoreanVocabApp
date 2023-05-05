package com.example.googlesheetskoreanvocabapp.usefulphrases

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.state.DisplayState
import com.example.googlesheetskoreanvocabapp.common.ui.LoadingState
import com.example.googlesheetskoreanvocabapp.common.ui.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayPhrasesScreen(getPhrasesViewModel: PhrasesViewModel = hiltViewModel()) {
    val allPhrases by getPhrasesViewModel.displayAllPairsUiState.collectAsState()
    when (val value = allPhrases) {
        is DisplayState.AllPairs -> ShowPairComposable(
            value.allPairs,
            SheetsHelper.WordType.USEFUL_PHRASES,
            getPhrasesViewModel::deleteWordPair
        )
        DisplayState.Loading -> LoadingState(wordType = SheetsHelper.WordType.USEFUL_PHRASES)
    }
}
