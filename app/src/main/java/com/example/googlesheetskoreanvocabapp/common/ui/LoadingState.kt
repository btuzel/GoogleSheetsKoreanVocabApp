package com.example.googlesheetskoreanvocabapp.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun LoadingState(wordType: SheetsHelper.WordType) {
    LocalFocusManager.current.clearFocus()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            strokeWidth = 16.dp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(128.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Loading ${wordType.name}", style = MaterialTheme.typography.h3)
    }
}