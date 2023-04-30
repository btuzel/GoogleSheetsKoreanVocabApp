package com.example.googlesheetskoreanvocabapp.complexsentences

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddComplexSentencesScreen(complexSentencesViewModel: GetComplexSentencesViewModel = hiltViewModel()) {
    AddPairComposable(
        addWord = complexSentencesViewModel::addWordPair,
        deleteWord = complexSentencesViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.COMPLEX_SENTENCES,
    )
}