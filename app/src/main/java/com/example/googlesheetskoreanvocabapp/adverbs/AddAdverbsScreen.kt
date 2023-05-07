package com.example.googlesheetskoreanvocabapp.adverbs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ui.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddAdverbsScreen(adverbsViewModel: AdverbsViewModel = hiltViewModel()) {
    val wordState = adverbsViewModel.wordState.collectAsState()
    AddPairComposable(
        addWord = adverbsViewModel::addWordPair,
        deleteWord = adverbsViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.ADVERBS,
        wordState = wordState.value
    )
}