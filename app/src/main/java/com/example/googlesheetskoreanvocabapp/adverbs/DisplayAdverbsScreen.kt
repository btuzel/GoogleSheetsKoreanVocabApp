package com.example.googlesheetskoreanvocabapp.adverbs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.googlesheetskoreanvocabapp.common.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayAdverbsScreen(adverbsViewModel: AdverbsViewModel = hiltViewModel()) {
    val allAdverbs by adverbsViewModel.uiState3.collectAsState()
    ShowPairComposable(allAdverbs.allAdverbs, SheetsHelper.WordType.ADVERBS, adverbsViewModel::deleteAdverbFromColumn)
}