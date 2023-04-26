package com.example.googlesheetskoreanvocabapp.nouns

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayNounsScreen(getNounsViewModel: NounsViewModel = hiltViewModel()) {
    val allNouns by getNounsViewModel.displayAllNounsUiState.collectAsState()
    ShowPairComposable(
        allNouns.allNouns,
        SheetsHelper.WordType.NOUNS,
        getNounsViewModel::deleteNounsFromColumn
    )
}
