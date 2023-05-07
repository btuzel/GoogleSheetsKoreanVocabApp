package com.example.googlesheetskoreanvocabapp.complexsentences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.state.DisplayState
import com.example.googlesheetskoreanvocabapp.common.ui.LoadingState
import com.example.googlesheetskoreanvocabapp.common.ui.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplaySentencesScreen(getComplexSentencesViewModel: GetComplexSentencesViewModel = hiltViewModel()) {
    val allSentences by getComplexSentencesViewModel.displayAllPairsUiState.collectAsState()
    val wordState = getComplexSentencesViewModel.wordState.collectAsState()
    when (val value = allSentences) {
        is DisplayState.AllPairs -> ShowPairComposable(
            pairList = value.allPairs,
            wordType = SheetsHelper.WordType.COMPLEX_SENTENCES,
            onDelete = getComplexSentencesViewModel::deleteWordPair,
            wordState = wordState.value
        )
        DisplayState.Loading -> LoadingState(wordType = SheetsHelper.WordType.COMPLEX_SENTENCES)
    }
}