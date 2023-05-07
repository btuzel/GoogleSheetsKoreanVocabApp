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
    val wordState = getPhrasesViewModel.wordState.collectAsState()
    when (val value = allPhrases) {
        is DisplayState.AllPairs -> ShowPairComposable(
            pairList = value.allPairs,
            wordType = SheetsHelper.WordType.USEFUL_PHRASES,
            onDelete = getPhrasesViewModel::deleteWordPair,
            wordState = wordState.value
        )
        DisplayState.Loading -> LoadingState(wordType = SheetsHelper.WordType.USEFUL_PHRASES)
    }
}
