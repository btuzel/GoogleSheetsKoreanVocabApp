package com.example.googlesheetskoreanvocabapp.common.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.googlesheetskoreanvocabapp.common.viewmodel.BaseWordPairViewModel
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import kotlinx.coroutines.launch

@Composable
fun AddPairComposable(
    addWord: (String, String) -> Unit,
    deleteWord: (String, String) -> Unit,
    wordType: SheetsHelper.WordType,
    wordState: BaseWordPairViewModel.WordState,
) {
    var englishWord by remember { mutableStateOf("") }
    var koreanWord by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 80.dp)
    ) {
        val context = LocalContext.current
        when (wordState) {
            is BaseWordPairViewModel.WordState.AddWordState -> {
                if (wordState.added) {
                    LaunchedEffect(wordState.word) {
                        scope.launch {
                            Toast.makeText(
                                context,
                                "${wordState.word} has been succesfully added to sheets.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            is BaseWordPairViewModel.WordState.DeleteWordState -> {
                if (wordState.added) {
                    LaunchedEffect(wordState.word) {
                        scope.launch {
                            Toast.makeText(
                                context,
                                "${wordState.word} has been succesfully deleted from sheets.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        Text(text = "Add ${wordType.name} pair", style = MaterialTheme.typography.h4)
        Divider(color = Color.White, thickness = 1.dp)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = englishWord,
            onValueChange = { englishWord = it },
            label = { Text("English word") },
            shape = RoundedCornerShape(16.dp),
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
            shape = RoundedCornerShape(16.dp),
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            AppButton(onClick = { addWord(englishWord, koreanWord) }, text = "Add")
            Spacer(modifier = Modifier.width(16.dp))
            AppButtonSecondary(onClick = {
                deleteWord(englishWord, koreanWord)
            }, text = "Remove")
        }
    }
}

@Composable
fun AppButton(onClick: () -> Unit, modifier: Modifier = Modifier, text: String, isEnabled: Boolean = true) {
    Button(
        modifier = modifier
            .height(48.dp)
            .widthIn(min = 60.dp),
        onClick = onClick,
        enabled = isEnabled
    ) {
        Text(text = text)
    }
}

@Composable
fun AppButtonSecondary(onClick: () -> Unit, modifier: Modifier = Modifier, text: String) {
    OutlinedButton(
        modifier = modifier
            .height(48.dp)
            .widthIn(min = 60.dp),
        border = BorderStroke(width = 2.dp, color = Color.White),
        onClick = onClick,
    ) {
        Text(text = text)
    }
}
