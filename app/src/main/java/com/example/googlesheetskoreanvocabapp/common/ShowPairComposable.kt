package com.example.googlesheetskoreanvocabapp.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun ShowPairComposable(
    pairList: Pair<List<String>, List<String>>,
    wordType: SheetsHelper.WordType
) {
    var searchQuery by remember { mutableStateOf("") }

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
                        Text(text = filteredSecondList[index])
                    }
                }
            }
        }
    }
}