package com.example.googlesheetskoreanvocabapp.positions

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddPositionsScreen(positionsViewModel: PositionsViewModel = hiltViewModel()) {
    AddPairComposable(
        addWord = positionsViewModel::addPositionsToColumn,
        deleteWord = positionsViewModel::deletePositionsFromColumn,
        wordType = SheetsHelper.WordType.POSITIONS
    )
}