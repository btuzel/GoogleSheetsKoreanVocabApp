package com.example.googlesheetskoreanvocabapp.verbs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayVerbsScreen(getVerbsViewModel: VerbsViewModel = hiltViewModel()) {
    val allVerbs by getVerbsViewModel.displayAllVerbsUiState.collectAsState()
    ShowPairComposable(allVerbs.allVerbs, SheetsHelper.WordType.VERBS, getVerbsViewModel::deleteVerbsFromColumn)
}
