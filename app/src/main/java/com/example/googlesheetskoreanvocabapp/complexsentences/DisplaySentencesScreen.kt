package com.example.googlesheetskoreanvocabapp.complexsentences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplaySentencesScreen(getComplexSentencesViewModel: GetComplexSentencesViewModel = hiltViewModel()) {
    val allSentences by getComplexSentencesViewModel.uiState3.collectAsState()
    ShowPairComposable(allSentences.allSentences, SheetsHelper.WordType.ADVERBS)
}