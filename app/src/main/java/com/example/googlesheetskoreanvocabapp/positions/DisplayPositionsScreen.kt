package com.example.googlesheetskoreanvocabapp.positions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.DisplayState
import com.example.googlesheetskoreanvocabapp.common.LoadingState
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayPositionsScreen(getPositionsViewModel: PositionsViewModel = hiltViewModel()) {
    val allPositions by getPositionsViewModel.displayAllPairsUiState.collectAsState()
    when (val value = allPositions) {
        is DisplayState.AllPairs -> ShowPairComposable(
            value.allPairs,
            SheetsHelper.WordType.POSITIONS,
            getPositionsViewModel::deleteWordPair
        )
        DisplayState.Loading -> LoadingState()
    }
}