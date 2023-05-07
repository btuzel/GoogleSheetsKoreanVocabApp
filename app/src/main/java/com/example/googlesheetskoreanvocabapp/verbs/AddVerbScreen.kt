package com.example.googlesheetskoreanvocabapp.verbs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ui.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddVerbScreen(verbsViewModel: VerbsViewModel = hiltViewModel()) {
    val state = verbsViewModel.wordState.collectAsState()
    AddPairComposable(
        addWord = verbsViewModel::addWordPair,
        deleteWord = verbsViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.VERBS,
        wordState = state.value
    )
}