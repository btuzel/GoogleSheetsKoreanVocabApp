package com.example.googlesheetskoreanvocabapp.nouns

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddNounsScreen(nounsViewModel: NounsViewModel = hiltViewModel()) {
    AddPairComposable(
        addWord = nounsViewModel::addNounsToColumn,
        deleteWord = nounsViewModel::deleteNounsFromColumn,
        wordType = SheetsHelper.WordType.NOUNS
    )
}