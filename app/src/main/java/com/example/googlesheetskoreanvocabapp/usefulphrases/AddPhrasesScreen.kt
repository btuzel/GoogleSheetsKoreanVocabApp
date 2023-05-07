package com.example.googlesheetskoreanvocabapp.usefulphrases

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ui.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddPhrasesScreen(phrasesViewModel: PhrasesViewModel = hiltViewModel()) {
    val state = phrasesViewModel.wordState.collectAsState()
    AddPairComposable(
        addWord = phrasesViewModel::addWordPair,
        deleteWord = phrasesViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.USEFUL_PHRASES,
        wordState = state.value
    )
}