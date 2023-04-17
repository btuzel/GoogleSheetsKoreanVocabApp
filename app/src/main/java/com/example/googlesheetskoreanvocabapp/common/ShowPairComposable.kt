package com.example.googlesheetskoreanvocabapp.common

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
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun ShowPairComposable(
    pairList: Pair<List<String>, List<String>>,
    wordType: SheetsHelper.WordType,
    onDelete: (String, String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showTranslations by remember { mutableStateOf(true) }

    val filteredFirstList = pairList.first.filterIndexed { index, it ->
        it.contains(searchQuery, ignoreCase = true)
    }

    val filteredSecondList = pairList.second.filterIndexed { index, it ->
        pairList.first[index].contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total amount of ${wordType.name} is ${filteredSecondList.size}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { showTranslations = !showTranslations }) {
                Text("Show/Hide translations")
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(filteredFirstList) { index, it ->
                    Row {
                        Text(text = (index + 1).toString() + "-")
                        Text(text = it, modifier = Modifier.padding(end = 32.dp))
                        Text(
                            text = filteredSecondList[index], color = if (showTranslations) {
                                Color.White
                            } else Color.Black
                        )
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.clickable {
                                onDelete(it, filteredSecondList[index])
                            }
                        )
                    }
                }
            }
        }
    }
}
