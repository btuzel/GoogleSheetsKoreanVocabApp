package com.example.googlesheetskoreanvocabapp.verbs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.state.DisplayState
import com.example.googlesheetskoreanvocabapp.common.ui.LoadingState
import com.example.googlesheetskoreanvocabapp.common.ui.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayVerbsScreen(getVerbsViewModel: VerbsViewModel = hiltViewModel()) {
    val allVerbs by getVerbsViewModel.displayAllPairsUiState.collectAsState()
    when (val value = allVerbs) {
        is DisplayState.AllPairs -> ShowPairComposable(
            value.allPairs,
            SheetsHelper.WordType.VERBS,
            getVerbsViewModel::deleteWordPair
        )
        DisplayState.Loading -> LoadingState(wordType = SheetsHelper.WordType.VERBS)
    }
}
