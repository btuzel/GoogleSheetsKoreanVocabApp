package com.example.googlesheetskoreanvocabapp.nouns

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ui.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddNounsScreen(nounsViewModel: NounsViewModel = hiltViewModel()) {
    val wordState = nounsViewModel.wordState.collectAsState()
    AddPairComposable(
        addWord = nounsViewModel::addWordPair,
        deleteWord = nounsViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.NOUNS,
        wordState = wordState.value
    )
}