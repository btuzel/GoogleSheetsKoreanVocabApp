package com.example.googlesheetskoreanvocabapp.verbs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddVerbScreen(verbsViewModel: VerbsViewModel = hiltViewModel()) {
    AddPairComposable(
        addWord = verbsViewModel::addWordPair,
        deleteWord = verbsViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.VERBS
    )
}