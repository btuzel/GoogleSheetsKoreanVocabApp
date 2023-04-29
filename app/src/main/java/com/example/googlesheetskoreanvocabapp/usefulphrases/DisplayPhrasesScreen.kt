package com.example.googlesheetskoreanvocabapp.usefulphrases

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.DisplayState
import com.example.googlesheetskoreanvocabapp.common.LoadingState
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayPhrasesScreen(getPhrasesViewModel: PhrasesViewModel = hiltViewModel()) {
    val allPhrases by getPhrasesViewModel.displayAllPhrasesUiState.collectAsState()
    when (val value = allPhrases) {
        is DisplayState.AllPairs -> ShowPairComposable(
            value.allPairs,
            SheetsHelper.WordType.POSITIONS,
            getPhrasesViewModel::deletePhrasesFromColumn
        )
        DisplayState.Loading -> LoadingState()
    }
}
