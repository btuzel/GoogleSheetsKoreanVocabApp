package com.example.googlesheetskoreanvocabapp.adverbs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddAdverbsScreen(adverbsViewModel: AdverbsViewModel = hiltViewModel()) {
    AddPairComposable(
        addWord = adverbsViewModel::addWordPair,
        deleteWord = adverbsViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.ADVERBS
    )
}