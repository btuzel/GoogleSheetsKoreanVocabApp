package com.example.googlesheetskoreanvocabapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.results.ResultsViewModel
import com.example.googlesheetskoreanvocabapp.ui.theme.ErrorRed

@Composable
fun ResultsScreen(resultsViewModel: ResultsViewModel = hiltViewModel()) {
    val collectedUiState = resultsViewModel.uiState.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(collectedUiState.value.data) { index, it ->
            val parts = splitString(it)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                verticalArrangement = Arrangement.Center
            ) {
                MistakesText(text = parts[0])
                Spacer(modifier = Modifier.width(8.dp))
                WordTypeText(text = parts[1])
                Spacer(modifier = Modifier.width(8.dp))
                DateText(text = parts[2])
            }
        }
    }
}

fun splitString(str: String): List<String> {
    return str.split("%")
}

@Composable
fun MistakesText(text: String) {
    val mistakesText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(color = Color.White)
        ) {
            append("Mistakes: ")
        }
        withStyle(
            style = SpanStyle(color = ErrorRed, fontWeight = FontWeight.Bold)
        ) {
            append(text)
        }
    }
    Text(text = mistakesText)
}

@Composable
fun WordTypeText(text: String) {
    val wordTypeText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(color = Color.White)
        ) {
            append("WordType: ")
        }
        withStyle(
            style = SpanStyle(color = ErrorRed, fontWeight = FontWeight.Bold)
        ) {
            append(text)
        }
    }
    Text(text = wordTypeText)
}

@Composable
fun DateText(text: String) {
    val dateText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(color = Color.White)
        ) {
            append("Date: ")
        }
        withStyle(
            style = SpanStyle(color = ErrorRed, fontWeight = FontWeight.Bold)
        ) {
            append(text)
        }
    }
    Text(text = dateText)
}