package com.example.googlesheetskoreanvocabapp.usefulphrases

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayPhrasesScreen(getPhrasesViewModel: PhrasesViewModel = hiltViewModel()) {
    val allPhrases by getPhrasesViewModel.uiState3.collectAsState()
    ShowPairComposable(allPhrases.allPhrases, SheetsHelper.WordType.USEFUL_PHRASES, getPhrasesViewModel::deletePhrasesFromColumn)
}
