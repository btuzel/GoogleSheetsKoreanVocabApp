package com.example.googlesheetskoreanvocabapp.nouns

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.DisplayState
import com.example.googlesheetskoreanvocabapp.common.LoadingState
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayNounsScreen(getNounsViewModel: NounsViewModel = hiltViewModel()) {
    val allNouns by getNounsViewModel.displayAllPairsUiState.collectAsState()
    when (val value = allNouns) {
        is DisplayState.AllPairs -> ShowPairComposable(
            value.allPairs,
            SheetsHelper.WordType.NOUNS,
            getNounsViewModel::deleteWordPair
        )
        DisplayState.Loading -> LoadingState()
    }
}
