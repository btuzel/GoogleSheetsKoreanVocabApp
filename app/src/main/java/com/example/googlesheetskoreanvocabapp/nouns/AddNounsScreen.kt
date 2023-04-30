package com.example.googlesheetskoreanvocabapp.nouns

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddNounsScreen(nounsViewModel: NounsViewModel = hiltViewModel()) {
    AddPairComposable(
        addWord = nounsViewModel::addWordPair,
        deleteWord = nounsViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.NOUNS
    )
}