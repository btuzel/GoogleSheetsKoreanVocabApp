package com.example.googlesheetskoreanvocabapp.complexsentences

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.AddPairComposable

@Composable
fun AddComplexSentencesScreen(complexSentencesViewModel: GetComplexSentencesViewModel = hiltViewModel()) {
    AddPairComposable(
        addWord = complexSentencesViewModel::addComplexSentencesToColumn,
        deleteWord = complexSentencesViewModel::deleteComplexSentencesFromColumn,
    )
}