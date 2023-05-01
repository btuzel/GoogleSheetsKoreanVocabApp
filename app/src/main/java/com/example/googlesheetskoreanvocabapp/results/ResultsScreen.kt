package com.example.googlesheetskoreanvocabapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.results.ResultsViewModel

@Composable
fun ResultsScreen(resultsViewModel: ResultsViewModel = hiltViewModel()) {

    val collectedUiState = resultsViewModel.uiState.collectAsState()

//    LazyColumn(
//        contentPadding = PaddingValues(16.dp),
//        verticalArrangement = Arrangement.spacedBy(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        itemsIndexed(collectedUiState.value.data) { index, it ->
//            Text(text = it)
//        }
//    }
    //TODO: convert into lazy column with split%, have screen for empty sharedpref
    Column(modifier = Modifier.fillMaxSize()) {
        collectedUiState.value.data.forEach {
            Text(text = it)
        }
    }
}