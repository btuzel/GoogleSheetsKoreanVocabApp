package com.example.googlesheetskoreanvocabapp.positions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayPositionsScreen(getPositionsViewModel: PositionsViewModel = hiltViewModel()) {
    val allPositions by getPositionsViewModel.displayAllPositionsUiState.collectAsState()
    ShowPairComposable(
        allPositions.allPositions,
        SheetsHelper.WordType.POSITIONS,
        getPositionsViewModel::deletePositionsFromColumn
    )
}