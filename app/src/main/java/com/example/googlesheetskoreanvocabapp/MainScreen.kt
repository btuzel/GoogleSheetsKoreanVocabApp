package com.example.googlesheetskoreanvocabapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.googlesheetskoreanvocabapp.common.ui.LinearLoadingState
import com.example.googlesheetskoreanvocabapp.navigation.ScreenDestination
import com.example.googlesheetskoreanvocabapp.ui.theme.CloudBurst
import com.example.googlesheetskoreanvocabapp.ui.theme.CyanCobaltBlue
import com.example.googlesheetskoreanvocabapp.ui.theme.SonicSilver

@Composable
fun MainScreen(
    navHostController: NavHostController,
    syncViewModel: SyncViewModel = hiltViewModel()
) {
    val uiState by syncViewModel.uiState.collectAsState()
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
            "Verb" to { navHostController.navigate(ScreenDestination.VerbsScreen.withArgs()) },
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

    WordManagementScreen(
        navHostController = navHostController,
        uiState = uiState,
        buttonGroups = buttonGroups,
        goToResults = { navHostController.navigate(ScreenDestination.ResultsScreen.route) },
        clearSharedPref = syncViewModel::clearSharedPref
    )
}

@Composable
fun WordManagementScreen(
    navHostController: NavHostController,
    buttonGroups: WordManagementButtonGroups,
    goToResults: () -> Unit,
    clearSharedPref: () -> Unit,
    uiState: SyncViewModel.SyncState
) {
    val categories = listOf(
        "Noun",
        "Adverb",
        "Verb",
        "Positions",
        "Useful Phrases",
        "Complex Sentences"
    )
    val showButtons = remember {
        mutableStateOf(true)
    }
    val showAdd = remember {
        mutableStateOf(false)
    }
    val showTest = remember {
        mutableStateOf(false)
    }
    val showDisplay = remember {
        mutableStateOf(false)
    }
    val showResults = remember {
        mutableStateOf(false)
    }

    when (uiState) {
        SyncViewModel.SyncState.Done -> Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            RaindropAnimation()
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                showButtons.value = true
                showDisplay.value = false
                showTest.value = false
                showResults.value = false
                showAdd.value = false
            }) {
                Text(text = "Go to Main Screen")
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                if (showButtons.value) {
                    Button(
                        onClick = {
                            showTest.value = true
                            showButtons.value = false
                        },
                        modifier = Modifier
                            .size(180.dp)
                            .padding(16.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = CloudBurst,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Test", style = MaterialTheme.typography.h3)
                    }
                    Button(
                        onClick = {
                            showAdd.value = true
                            showButtons.value = false
                        },
                        modifier = Modifier
                            .size(180.dp)
                            .padding(16.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Add", style = MaterialTheme.typography.h3)
                    }
                    Button(
                        onClick = {
                            showDisplay.value = true
                            showButtons.value = false
                        },
                        modifier = Modifier
                            .size(180.dp)
                            .padding(16.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = SonicSilver,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Display", style = MaterialTheme.typography.h3)
                    }
                    Button(
                        onClick = {
                            showResults.value = true
                            showButtons.value = false
                        },
                        modifier = Modifier
                            .size(180.dp)
                            .padding(16.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = CyanCobaltBlue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Results", style = MaterialTheme.typography.h3)
                    }
                } else {
                    if (showTest.value) {
                        categories.forEach { category ->
                            buttonGroups.testButtonGroup[category]?.let { testButton ->
                                if (category != "Verb") {
                                    Text(
                                        text = "Test $category",
                                        style = MaterialTheme.typography.h4
                                    )
                                    Button(onClick = testButton) {
                                        Text("Test $category")
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                } else {
                                    Text(
                                        text = "Test $category",
                                        style = MaterialTheme.typography.h4
                                    )
                                    Row {
                                        Button(onClick = {
                                            navHostController.navigate(
                                                ScreenDestination.VerbsScreen.returnVerbsRoute(80,0)
                                            )
                                        }) {
                                            Text("Test $category old")
                                        }
                                        Spacer(modifier = Modifier.width(32.dp))
                                        Button(onClick = {
                                            navHostController.navigate(
                                                ScreenDestination.VerbsScreen.returnVerbsRoute(2000,80)
                                            )
                                        }) {
                                            Text("Test $category new")
                                        }
                                        Spacer(modifier = Modifier.width(32.dp))
                                        Button(onClick = {
                                            navHostController.navigate(
                                                ScreenDestination.VerbsScreen.returnVerbsRoute(2000,0)
                                            )
                                        }) {
                                            Text("Test $category all")
                                        }
                                        Spacer(modifier = Modifier.height(32.dp))
                                    }
                                }
                            }
                        }
                    } else if (showDisplay.value) {
                        categories.forEach { category ->
                            buttonGroups.displayButtonGroup[category]?.let { displayButton ->
                                Text(
                                    text = "Display $category",
                                    style = MaterialTheme.typography.h4
                                )
                                Button(onClick = displayButton) {
                                    Text("Display $category")
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    } else if (showAdd.value) {
                        categories.forEach { category ->
                            buttonGroups.addButtonGroup[category]?.let { addButton ->
                                Text(text = "Add $category", style = MaterialTheme.typography.h4)
                                Button(onClick = addButton) {
                                    Text("Add $category")
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    } else if (showResults.value) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = goToResults) {
                                Text(text = "Show Results", style = MaterialTheme.typography.h4)
                            }
                            Spacer(modifier = Modifier.height(48.dp))
                            Button(onClick = clearSharedPref) {
                                Text(
                                    text = "Clear Shared Preferences",
                                    style = MaterialTheme.typography.h4
                                )
                            }
                        }
                    }
                }
            }
        }

        SyncViewModel.SyncState.Init -> {}
        is SyncViewModel.SyncState.Loading -> LinearLoadingState(
            wordType = uiState.wordType,
            progress = uiState.percentage
        )
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
        modifier = Modifier
            .size(70.dp)
            .background(Color.White, shape = CircleShape),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}