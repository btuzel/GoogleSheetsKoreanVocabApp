package com.example.googlesheetskoreanvocabapp.verbs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.AddPairComposable

@Composable
fun AddVerbScreen(verbsViewModel: VerbsViewModel = hiltViewModel()) {
    AddPairComposable(
        addWord = verbsViewModel::addVerbToColumn,
        deleteWord = verbsViewModel::deleteVerbsFromColumn
    )
}