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
        testButtonGroup = TestButtonGroup(
            noun = { navHostController.navigate(ScreenDestination.NounsScreen.route) },
            adverb = { navHostController.navigate(ScreenDestination.AdverbsScreen.route) },
            verb = { navHostController.navigate(ScreenDestination.VerbsScreen.route) },
            positions = { navHostController.navigate(ScreenDestination.PositionsScreen.route) },
            phrases = { navHostController.navigate(ScreenDestination.UsefulPhrasesScreen.route) },
            complexSentences = { navHostController.navigate(ScreenDestination.ComplexSentencesScreen.route) },
        ),
        addButtonGroup = AddButtonGroup(
            noun = { navHostController.navigate(ScreenDestination.AddNounsScreen.route) },
            adverb = { navHostController.navigate(ScreenDestination.AddAdverbsScreen.route) },
            verb = { navHostController.navigate(ScreenDestination.AddVerbsScreen.route) },
            positions = { navHostController.navigate(ScreenDestination.AddPositionsScreen.route) },
            phrases = { navHostController.navigate(ScreenDestination.AddUsefulPhrasesScreen.route) },
            complexSentences = { navHostController.navigate(ScreenDestination.AddComplexSentencesScreen.route) },
        ),
        displayButtonGroup = DisplayButtonGroup(
            noun = { navHostController.navigate(ScreenDestination.DisplayNounsScreen.route) },
            adverb = { navHostController.navigate(ScreenDestination.DisplayAdverbsScreen.route) },
            verb = { navHostController.navigate(ScreenDestination.DisplayVerbsScreen.route) },
            positions = { navHostController.navigate(ScreenDestination.DisplayPositionsScreen.route) },
            phrases = { navHostController.navigate(ScreenDestination.DisplayUsefulPhrasesScreen.route) },
            complexSentences = { navHostController.navigate(ScreenDestination.DisplayComplexSentencesScreen.route) },
        )
    )
}

@Composable
fun CombinedButtons(
    addButtonGroup: AddButtonGroup,
    displayButtonGroup: DisplayButtonGroup,
    testButtonGroup: TestButtonGroup,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Add Words", style = MaterialTheme.typography.h2)
        Button(onClick = addButtonGroup.noun) {
            Text("Add Noun")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = addButtonGroup.adverb) {
            Text("Add Adverb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = addButtonGroup.verb) {
            Text("Add Verb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = addButtonGroup.positions) {
            Text("Add Positions")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = addButtonGroup.phrases) {
            Text("Add Useful Phrases")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = addButtonGroup.complexSentences) {
            Text("Add Complex Sentences")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Test Words", style = MaterialTheme.typography.h2)
        Button(onClick = testButtonGroup.noun) {
            Text("Noun")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = testButtonGroup.adverb) {
            Text("Adverb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = testButtonGroup.verb) {
            Text("Verb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = testButtonGroup.positions) {
            Text("Positions")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = testButtonGroup.phrases) {
            Text("Useful Phrases")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = testButtonGroup.complexSentences) {
            Text("Complex Sentences")
        }
        Text(text = "Display Words", style = MaterialTheme.typography.h2)
        Button(onClick = displayButtonGroup.noun) {
            Text("Display Noun")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = displayButtonGroup.adverb) {
            Text("Display Adverb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = displayButtonGroup.verb) {
            Text("Display Verb")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = displayButtonGroup.positions) {
            Text("Display Positions")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = displayButtonGroup.phrases) {
            Text("Display Useful Phrases")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = displayButtonGroup.complexSentences) {
            Text("Display Complex Sentences")
        }
    }
}

data class TestButtonGroup(
    val noun: () -> Unit,
    val adverb: () -> Unit,
    val verb: () -> Unit,
    val positions: () -> Unit,
    val phrases: () -> Unit,
    val complexSentences: () -> Unit,
)

data class AddButtonGroup(
    val noun: () -> Unit,
    val adverb: () -> Unit,
    val verb: () -> Unit,
    val positions: () -> Unit,
    val phrases: () -> Unit,
    val complexSentences: () -> Unit,
)

data class DisplayButtonGroup(
    val noun: () -> Unit,
    val adverb: () -> Unit,
    val verb: () -> Unit,
    val positions: () -> Unit,
    val phrases: () -> Unit,
    val complexSentences: () -> Unit
)