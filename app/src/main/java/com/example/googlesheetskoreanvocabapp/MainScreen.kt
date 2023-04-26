package com.example.googlesheetskoreanvocabapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.googlesheetskoreanvocabapp.navigation.ScreenDestination

@Composable
fun MainScreen(
    navHostController: NavHostController,
    syncViewModel: SyncViewModel = hiltViewModel()
) {

    CombinedButtons(
        onNounButtonClick = { navHostController.navigate(ScreenDestination.NounsScreen.route) },
        onAdverbButtonClick = { navHostController.navigate(ScreenDestination.AdverbsScreen.route) },
        onVerbButtonClick = { navHostController.navigate(ScreenDestination.VerbsScreen.route) },
        onPositionsButtonClick = { navHostController.navigate(ScreenDestination.PositionsScreen.route) },
        onPhrasesButtonClick = { navHostController.navigate(ScreenDestination.UsefulPhrasesScreen.route) },
        onComplexSentencesClick = { navHostController.navigate(ScreenDestination.ComplexSentencesScreen.route) },
        onAddNounButtonClick = { navHostController.navigate(ScreenDestination.AddNounsScreen.route) },
        onAddAdverbButtonClick = { navHostController.navigate(ScreenDestination.AddAdverbsScreen.route) },
        onAddVerbButtonClick = { navHostController.navigate(ScreenDestination.AddVerbsScreen.route) },
        onAddPositionsButtonClick = { navHostController.navigate(ScreenDestination.AddPositionsScreen.route) },
        onAddPhrasesButtonClick = { navHostController.navigate(ScreenDestination.AddUsefulPhrasesScreen.route) },
        onAddComplexSentencesClick = { navHostController.navigate(ScreenDestination.AddComplexSentencesScreen.route) },
        onDisplayNounButtonClick = { navHostController.navigate(ScreenDestination.DisplayNounsScreen.route) },
        onDisplayAdverbButtonClick = { navHostController.navigate(ScreenDestination.DisplayAdverbsScreen.route) },
        onDisplayVerbButtonClick = { navHostController.navigate(ScreenDestination.DisplayVerbsScreen.route) },
        onDisplayPositionsButtonClick = { navHostController.navigate(ScreenDestination.DisplayPositionsScreen.route) },
        onDisplayPhrasesButtonClick = { navHostController.navigate(ScreenDestination.DisplayUsefulPhrasesScreen.route) },
        onDisplayComplexSentencesClick = { navHostController.navigate(ScreenDestination.DisplayComplexSentencesScreen.route) },
    )
}

@Composable
fun CombinedButtons(
    onAddNounButtonClick: () -> Unit,
    onAddAdverbButtonClick: () -> Unit,
    onAddVerbButtonClick: () -> Unit,
    onAddPositionsButtonClick: () -> Unit,
    onAddPhrasesButtonClick: () -> Unit,
    onAddComplexSentencesClick: () -> Unit,
    onDisplayNounButtonClick: () -> Unit,
    onDisplayAdverbButtonClick: () -> Unit,
    onDisplayVerbButtonClick: () -> Unit,
    onDisplayPositionsButtonClick: () -> Unit,
    onDisplayPhrasesButtonClick: () -> Unit,
    onDisplayComplexSentencesClick: () -> Unit,
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
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Add Words", style = MaterialTheme.typography.h2)
        Button(onClick = onAddNounButtonClick) {
            Text("Add Noun")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddAdverbButtonClick) {
            Text("Add Adverb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddVerbButtonClick) {
            Text("Add Verb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddPositionsButtonClick) {
            Text("Add Positions")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddPhrasesButtonClick) {
            Text("Add Useful Phrases")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAddComplexSentencesClick) {
            Text("Add Complex Sentences")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Test Words", style = MaterialTheme.typography.h2)
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
        Text(text = "Display Words", style = MaterialTheme.typography.h2)
        Button(onClick = onDisplayNounButtonClick) {
            Text("Display Noun")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onDisplayAdverbButtonClick) {
            Text("Display Adverb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onDisplayVerbButtonClick) {
            Text("Display Verb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onDisplayPositionsButtonClick) {
            Text("Display Positions")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onDisplayPhrasesButtonClick) {
            Text("Display Useful Phrases")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onDisplayComplexSentencesClick) {
            Text("Display Complex Sentences")
        }
    }
}