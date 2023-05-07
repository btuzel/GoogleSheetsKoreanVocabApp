package com.example.googlesheetskoreanvocabapp.positions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ui.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddPositionsScreen(positionsViewModel: PositionsViewModel = hiltViewModel()) {
    val wordState = positionsViewModel.wordState.collectAsState()
    AddPairComposable(
        addWord = positionsViewModel::addWordPair,
        deleteWord = positionsViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.POSITIONS,
        wordState = wordState.value
    )
}