package com.example.googlesheetskoreanvocabapp.complexsentences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ui.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddComplexSentencesScreen(complexSentencesViewModel: GetComplexSentencesViewModel = hiltViewModel()) {
    val wordState = complexSentencesViewModel.wordState.collectAsState()
    AddPairComposable(
        addWord = complexSentencesViewModel::addWordPair,
        deleteWord = complexSentencesViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.COMPLEX_SENTENCES,
        wordState = wordState.value,
    )
}