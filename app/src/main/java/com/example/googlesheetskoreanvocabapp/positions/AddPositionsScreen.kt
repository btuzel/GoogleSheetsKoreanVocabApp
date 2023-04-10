package com.example.googlesheetskoreanvocabapp.positions

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddPositionsScreen(positionsViewModel: PositionsViewModel = hiltViewModel()) {
    AddPositionsCheckComposable(
        addWord = positionsViewModel::addPositionsToColumn,
        deleteWord = positionsViewModel::deletePositionsFromColumn
    )
}

@Composable
fun AddPositionsCheckComposable(
    addWord: (String, String) -> Unit,
    deleteWord: (String, String) -> Unit

) {
    var englishWord by remember { mutableStateOf("") }
    var koreanWord by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "ADD POSITIONS", style = MaterialTheme.typography.h3)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = englishWord,
            onValueChange = { englishWord = it },
            label = { Text("English word") },
            modifier = Modifier.width(250.dp),
            trailingIcon = {
                IconButton(
                    onClick = { englishWord = "" }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = koreanWord,
            onValueChange = { koreanWord = it },
            label = { Text("Korean word") },
            modifier = Modifier.width(250.dp),
            trailingIcon = {
                IconButton(
                    onClick = { koreanWord = "" }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear"
                    )
                }
            }
        )
        Button(onClick = { addWord(englishWord, koreanWord) }) {
            Text("Add")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { deleteWord(englishWord, koreanWord) }) {
            Text("Undo")
        }
    }
}