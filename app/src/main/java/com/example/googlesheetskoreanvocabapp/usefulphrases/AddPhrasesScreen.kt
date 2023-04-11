package com.example.googlesheetskoreanvocabapp.usefulphrases

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.AddPairComposable

@Composable
fun AddPhrasesScreen(phrasesViewModel: PhrasesViewModel = hiltViewModel()) {
    AddPairComposable(
        addWord = phrasesViewModel::addPhrasesToColumn,
        deleteWord = phrasesViewModel::deletePhrasesFromColumn
    )
}