package com.example.googlesheetskoreanvocabapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.googlesheetskoreanvocabapp.navigation.ScreenDestination

@Composable
fun MainScreen(navHostController: NavHostController) {
    FourButtonComposable(
        onNounButtonClick = { navHostController.navigate(ScreenDestination.NounsScreen.route) },
        onAdverbButtonClick = { navHostController.navigate(ScreenDestination.AdverbsScreen.route) },
        onVerbButtonClick = { navHostController.navigate(ScreenDestination.VerbsScreen.route) },
        onPositionsButtonClick = { navHostController.navigate(ScreenDestination.PositionsScreen.route) },
        onPhrasesButtonClick = { navHostController.navigate(ScreenDestination.UsefulPhrasesScreen.route) },
        onComplexSentencesClick = { navHostController.navigate(ScreenDestination.ComplexSentencesScreen.route) },
    )
}

@Composable
fun FourButtonComposable(
    onNounButtonClick: () -> Unit,
    onAdverbButtonClick: () -> Unit,
    onVerbButtonClick: () -> Unit,
    onPositionsButtonClick: () -> Unit,
    onPhrasesButtonClick: () -> Unit,
    onComplexSentencesClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = onNounButtonClick) {
            Text("Noun")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAdverbButtonClick) {
            Text("Adverb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onVerbButtonClick) {
            Text("Verb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onPositionsButtonClick) {
            Text("Positions")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onPhrasesButtonClick) {
            Text("Useful Phrases")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onComplexSentencesClick) {
            Text("Complex Sentences")
        }
    }
}