package com.example.googlesheetskoreanvocabapp.usefulphrases

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddPhrasesScreen(phrasesViewModel: PhrasesViewModel=hiltViewModel()) {
   AddPhrasesCheckComposable(
       addWord = phrasesViewModel::addPhrasesToColumn,
       deleteWord = phrasesViewModel::deletePhrasesFromColumn
   )
}

@Composable
fun AddPhrasesCheckComposable(
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
        Text(text = "ADD PHRASES", style = MaterialTheme.typography.h3)
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