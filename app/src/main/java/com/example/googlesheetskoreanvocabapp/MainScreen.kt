package com.example.googlesheetskoreanvocabapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.googlesheetskoreanvocabapp.navigation.ScreenDestination

@Composable
fun MainScreen(
    navHostController: NavHostController,
    syncViewModel: SyncViewModel = hiltViewModel()
) {

    val buttonGroups = WordManagementButtonGroups(
        addButtonGroup = mapOf(
            "Noun" to { navHostController.navigate(ScreenDestination.AddNounsScreen.route) },
            "Adverb" to { navHostController.navigate(ScreenDestination.AddAdverbsScreen.route) },
            "Verb" to { navHostController.navigate(ScreenDestination.AddVerbsScreen.route) },
            "Positions" to { navHostController.navigate(ScreenDestination.AddPositionsScreen.route) },
            "Useful Phrases" to { navHostController.navigate(ScreenDestination.AddUsefulPhrasesScreen.route) },
            "Complex Sentences" to { navHostController.navigate(ScreenDestination.AddComplexSentencesScreen.route) },
        ),
        testButtonGroup = mapOf(
            "Noun" to { navHostController.navigate(ScreenDestination.NounsScreen.route) },
            "Adverb" to { navHostController.navigate(ScreenDestination.AdverbsScreen.route) },
            "Verb" to { navHostController.navigate(ScreenDestination.VerbsScreen.route) },
            "Positions" to { navHostController.navigate(ScreenDestination.PositionsScreen.route) },
            "Useful Phrases" to { navHostController.navigate(ScreenDestination.UsefulPhrasesScreen.route) },
            "Complex Sentences" to { navHostController.navigate(ScreenDestination.ComplexSentencesScreen.route) },
        ),
        displayButtonGroup = mapOf(
            "Noun" to { navHostController.navigate(ScreenDestination.DisplayNounsScreen.route) },
            "Adverb" to { navHostController.navigate(ScreenDestination.DisplayAdverbsScreen.route) },
            "Verb" to { navHostController.navigate(ScreenDestination.DisplayVerbsScreen.route) },
            "Positions" to { navHostController.navigate(ScreenDestination.DisplayPositionsScreen.route) },
            "Useful Phrases" to { navHostController.navigate(ScreenDestination.DisplayUsefulPhrasesScreen.route) },
            "Complex Sentences" to { navHostController.navigate(ScreenDestination.DisplayComplexSentencesScreen.route) },
        ),
    )

    WordManagementScreen(buttonGroups)
}

@Composable
fun WordManagementScreen(buttonGroups: WordManagementButtonGroups) {
    val categories = listOf(
        "Noun",
        "Adverb",
        "Verb",
        "Positions",
        "Useful Phrases",
        "Complex Sentences"
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        RaindropAnimation()
        categories.forEach { category ->
            buttonGroups.displayButtonGroup[category]?.let { displayButton ->
                Text(text = "Display $category", style = MaterialTheme.typography.h4)
                Button(onClick = displayButton) {
                    Text("Display $category")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        categories.forEach { category ->
            buttonGroups.testButtonGroup[category]?.let { testButton ->
                Text(text = "Test $category", style = MaterialTheme.typography.h4)
                Button(onClick = testButton) {
                    Text("Test $category")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        categories.forEach { category ->
            buttonGroups.addButtonGroup[category]?.let { addButton ->
                Text(text = "Add $category", style = MaterialTheme.typography.h4)
                Button(onClick = addButton) {
                    Text("Add $category")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

data class WordManagementButtonGroups(
    val addButtonGroup: Map<String, () -> Unit>,
    val testButtonGroup: Map<String, () -> Unit>,
    val displayButtonGroup: Map<String, () -> Unit>
)

@Composable
fun RaindropAnimation() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.koreaaaa))
    LottieAnimation(
        modifier = Modifier.size(100.dp).background(Color.White),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}