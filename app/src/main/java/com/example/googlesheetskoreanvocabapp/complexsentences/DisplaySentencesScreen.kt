package com.example.googlesheetskoreanvocabapp.complexsentences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.state.DisplayState
import com.example.googlesheetskoreanvocabapp.common.LoadingState
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplaySentencesScreen(getComplexSentencesViewModel: GetComplexSentencesViewModel = hiltViewModel()) {
    val allSentences by getComplexSentencesViewModel.displayAllPairsUiState.collectAsState()
    when (val value = allSentences) {
        is DisplayState.AllPairs -> ShowPairComposable(
            value.allPairs,
            SheetsHelper.WordType.COMPLEX_SENTENCES,
            getComplexSentencesViewModel::deleteWordPair
        )
        DisplayState.Loading -> LoadingState()
    }
}