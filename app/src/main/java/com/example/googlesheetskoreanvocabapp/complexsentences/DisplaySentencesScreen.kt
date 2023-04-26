package com.example.googlesheetskoreanvocabapp.complexsentences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplaySentencesScreen(getComplexSentencesViewModel: GetComplexSentencesViewModel = hiltViewModel()) {
    val allSentences = getComplexSentencesViewModel.displayAllSentencesUiState.collectAsState()
    ShowPairComposable(
        allSentences.value.allSentences,
        SheetsHelper.WordType.COMPLEX_SENTENCES,
        getComplexSentencesViewModel::deleteComplexSentencesFromColumn
    )
}