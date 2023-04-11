package com.example.googlesheetskoreanvocabapp.adverbs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.AddPairComposable

@Composable
fun AddAdverbsScreen(adverbsViewModel: AdverbsViewModel = hiltViewModel()) {
    AddPairComposable(
        addWord = adverbsViewModel::addAdverbsToColumn,
        deleteWord = adverbsViewModel::deleteAdverbFromColumn
    )
}