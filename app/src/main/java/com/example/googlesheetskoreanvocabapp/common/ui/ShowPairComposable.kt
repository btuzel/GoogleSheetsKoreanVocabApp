package com.example.googlesheetskoreanvocabapp.common.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.googlesheetskoreanvocabapp.common.viewmodel.BaseWordPairViewModel
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.ui.theme.ErrorRed
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun ShowPairComposable(
    pairList: Pair<List<String>, List<String>>,
    wordType: SheetsHelper.WordType,
    onDelete: (String, String) -> Unit,
    wordState: BaseWordPairViewModel.WordState
) {
    var searchQuery by remember { mutableStateOf("") }
    var showTranslations by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    when (wordState) {
        is BaseWordPairViewModel.WordState.AddWordState -> {
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
    val filteredFirstList = pairList.first.filterIndexed { _, it ->
        it.contains(searchQuery, ignoreCase = true)
    }

    val filteredSecondList = pairList.second.filterIndexed { index, _ ->
        pairList.first[index].contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Total number of ${wordType.name} is ${pairList.first.size}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(32.dp))
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { showTranslations = !showTranslations }) {
                Text("Show/Hide translations")
            }
        }
        if (wordType == SheetsHelper.WordType.COMPLEX_SENTENCES) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(filteredFirstList) { index, it ->
                        val snooze = SwipeAction(
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete",
                                )
                            },
                            background = ErrorRed,
                            isUndo = true,
                            onSwipe = { onDelete(it, filteredSecondList[index]) },
                        )
                        SwipeableActionsBox(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            endActions = listOf(snooze)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row {
                                    Text(text = (index + 1).toString() + "-")
                                    Text(text = it)
                                }
                                Row {
                                    Text(
                                        text = filteredSecondList[index],
                                        lineHeight = 44.sp,
                                        color = if (showTranslations) {
                                            Color.White
                                        } else Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(filteredFirstList) { index, it ->
                        val snooze = SwipeAction(
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "Delete",
                                )
                            },
                            background = ErrorRed,
                            isUndo = true,
                            onSwipe = { onDelete(it, filteredSecondList[index]) },
                        )
                        SwipeableActionsBox(
                            endActions = listOf(snooze)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = (index + 1).toString() + "-")
                                Text(text = it, modifier = Modifier.padding(end = 32.dp))
                                Text(
                                    text = filteredSecondList[index],
                                    color = if (showTranslations) {
                                        Color.White
                                    } else Color.Black
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
