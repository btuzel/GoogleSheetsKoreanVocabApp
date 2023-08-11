package com.example.googlesheetskoreanvocabapp.common.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun KoreanButton(
    onClick: () -> Unit,
    backgroundColor: Color,
    buttonText: String
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .size(180.dp)
            .padding(8.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = Color.White
        )
    ) {
        Text(text = buttonText, style = MaterialTheme.typography.h3)
    }
}