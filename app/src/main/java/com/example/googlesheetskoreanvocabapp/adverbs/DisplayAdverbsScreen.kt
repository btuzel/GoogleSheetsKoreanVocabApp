package com.example.googlesheetskoreanvocabapp.adverbs

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayAdverbsScreen(adverbsViewModel: AdverbsViewModel = hiltViewModel()) {
    val allAdverbs by adverbsViewModel.displayAllAdverbsUiState.collectAsState()
    ShowPairComposable(allAdverbs.allAdverbs, SheetsHelper.WordType.ADVERBS, adverbsViewModel::deleteAdverbFromColumn)
}