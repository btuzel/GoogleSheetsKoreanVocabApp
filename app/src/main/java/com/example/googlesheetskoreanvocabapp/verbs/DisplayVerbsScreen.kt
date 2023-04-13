package com.example.googlesheetskoreanvocabapp.verbs

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
fun DisplayVerbsScreen(getVerbsViewModel: VerbsViewModel = hiltViewModel()) {
    val allVerbs by getVerbsViewModel.uiState3.collectAsState()
    ShowVerbsComposable(allVerbs.allVerbs)
}

@Composable
fun ShowVerbsComposable(showVerbs: Pair<List<String>, List<String>>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Total amount of Verbs is ${showVerbs.second.size}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(showVerbs.first) { index, it ->
                Row {
                    Text(text = (index + 1).toString() + "-")
                    Text(text = it, modifier = Modifier.padding(end = 32.dp))
                    Text(text = showVerbs.second.get(index))
                }
            }
        }
    }
}
