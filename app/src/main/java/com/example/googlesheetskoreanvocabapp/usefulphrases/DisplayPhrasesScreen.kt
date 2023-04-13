package com.example.googlesheetskoreanvocabapp.usefulphrases

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DisplayPhrasesScreen(getPhrasesViewModel: PhrasesViewModel = hiltViewModel()) {
    val allPhrases by getPhrasesViewModel.uiState3.collectAsState()
    ShowPhrasesComposable(allPhrases.allPhrases)
}

@Composable
fun ShowPhrasesComposable(showPhrases: Pair<List<String>, List<String>>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Total amount of Phrases is ${showPhrases.second.size}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(showPhrases.first) { index, it ->
                Row {
                    Text(text = (index + 1).toString() + "-")
                    Text(text = it, modifier = Modifier.padding(end = 32.dp))
                    Text(text = showPhrases.second.get(index))
                }
            }
        }
    }
}
