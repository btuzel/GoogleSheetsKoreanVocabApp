package com.example.googlesheetskoreanvocabapp.nouns

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.AddPairComposable

@Composable
fun AddNounsScreen(nounsViewModel: NounsViewModel = hiltViewModel()) {
    AddPairComposable(
        addWord = nounsViewModel::addNounsToColumn,
        deleteWord = nounsViewModel::deleteNounsFromColumn
    )
}