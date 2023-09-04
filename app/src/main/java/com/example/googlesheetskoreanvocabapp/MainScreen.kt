package com.example.googlesheetskoreanvocabapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.googlesheetskoreanvocabapp.common.VerbGroupType
import com.example.googlesheetskoreanvocabapp.common.ui.KoreanButton
import com.example.googlesheetskoreanvocabapp.common.ui.LinearLoadingState
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.navigation.ScreenDestination
import com.example.googlesheetskoreanvocabapp.ui.theme.CyanCobaltBlue
import com.example.googlesheetskoreanvocabapp.ui.theme.SonicSilver
import com.example.googlesheetskoreanvocabapp.ui.theme.Teal200

@Composable
fun MainScreen(
    navHostController: NavHostController,
    syncViewModel: SyncViewModel = hiltViewModel()
) {
    val uiState by syncViewModel.uiState.collectAsState()

    WordManagementScreen(
        uiState = uiState,
        goToNumbers = {
            navHostController.navigate(
                route = ScreenDestination.NumbersScreen.passType(
                    it.toString()
                )
            )
        },
        goToResults = { navHostController.navigate(ScreenDestination.ResultsScreen.route) },
        clearSharedPref = syncViewModel::clearSharedPref,
        goTestVerb = { navHostController.navigate(route = ScreenDestination.VerbsScreen.passType(it.name)) },
        goToDisplay = { navHostController.navigate(ScreenDestination.DisplayVerbsScreen.route) },
        goToAdd = { navHostController.navigate(ScreenDestination.AddVerbsScreen.route) }
    )
}

@Composable
fun WordManagementScreen(
    goToResults: () -> Unit,
    goToNumbers: (Boolean) -> Unit,
    goToDisplay: () -> Unit,
    goToAdd: () -> Unit,
    goTestVerb: (VerbGroupType) -> Unit,
    clearSharedPref: () -> Unit,
    uiState: SyncViewModel.SyncState
) {
    when (uiState) {
        SyncViewModel.SyncState.Done -> DoneStateScreen(
            goToNumbers = goToNumbers,
            goTestVerb = goTestVerb,
            goToAdd = goToAdd,
            goToDisplay = goToDisplay,
            goToResults = goToResults,
            clearSharedPref = clearSharedPref
        )

        SyncViewModel.SyncState.Init -> InitStateScreen()
        is SyncViewModel.SyncState.Loading -> LoadingStateScreen(
            uiState.wordType,
            uiState.percentage
        )
    }
}

@Composable
fun DoneStateScreen(
    goToNumbers: (Boolean) -> Unit,
    goTestVerb: (VerbGroupType) -> Unit,
    goToAdd: () -> Unit,
    goToDisplay: () -> Unit,
    goToResults: () -> Unit,
    clearSharedPref: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RaindropAnimation()

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { goToNumbers(false) }) {
            Text(text = "SinoKorean numbers")
        }

        Button(onClick = { goToNumbers(true) }) {
            Text(text = "PureKorean numbers")
        }

        TestVerbButtons(
            goTestVerb = goTestVerb
        )

        KoreanButtonsGroup(goToAdd = goToAdd, goToDisplay = goToDisplay, goToResults = goToResults)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = clearSharedPref) {
            Text(
                text = "Clear Shared Preferences",
                style = MaterialTheme.typography.h4
            )
        }
    }
}

@Composable
fun TestVerbButtons(
    goTestVerb: (VerbGroupType) -> Unit
) {
    Text(
        text = "Test Verb",
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(top = 16.dp)
    )

    Row {
        Button(onClick = {
            goTestVerb(VerbGroupType.ALL)
        }) {
            Text("Test all")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = {
            goTestVerb(VerbGroupType.OLD)
        }) {
            Text("Test old")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = {
            goTestVerb(VerbGroupType.HYUNGSEOK)
        }) {
            Text("Test HYUNGSEOK")
        }
        Spacer(modifier = Modifier.height(8.dp))
    }

    Row {
        Button(onClick = {
            goTestVerb(VerbGroupType.COLORS)
        }) {
            Text("Test COLOR")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = {
            goTestVerb(VerbGroupType.UPDOWNLEFTRIGHT)
        }) {
            Text("Test UPDOWN")
        }
        Button(onClick = {
            goTestVerb(VerbGroupType.WEEKDAYS)
        }) {
            Text("Test DAYSOFWEEK")
        }
        Spacer(modifier = Modifier.width(8.dp))
    }

    Row {
        Button(onClick = {
            goTestVerb(VerbGroupType.ANIMAL)
        }) {
            Text("Test ANIMAL")
        }

        Button(onClick = {
            goTestVerb(VerbGroupType.BODYPARTS)
        }) {
            Text("Test BODYPARTS")
        }

        Button(onClick = {
            goTestVerb(VerbGroupType.YUUN)
        }) {
            Text("Test YUUN")
        }
    }
    var fromIndex by remember { mutableStateOf("") }
    var toIndex by remember { mutableStateOf("") }
    Row {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = fromIndex,
                onValueChange = { fromIndex = it },
                shape = RoundedCornerShape(16.dp),
                label = { Text("From") },
            )
            OutlinedTextField(
                value = toIndex,
                onValueChange = { toIndex = it },
                shape = RoundedCornerShape(16.dp),
                label = { Text("To") },
            )
            Button(onClick = {
                GlobalClass.globalProperty = Pair(fromIndex.toInt(), toIndex.toInt())
                goTestVerb(VerbGroupType.LASTXNUMBERS)
            }) {
                Text("Last Lesson")
            }
        }
    }
}

class GlobalClass {
    companion object {
        var globalProperty: Pair<Int, Int> = Pair(0, 0)
    }
}

@Composable
fun KoreanButtonsGroup(
    goToAdd: () -> Unit,
    goToDisplay: () -> Unit,
    goToResults: () -> Unit
) {
    KoreanButton(
        onClick = { goToAdd() },
        backgroundColor = Teal200,
        buttonText = "Add"
    )

    KoreanButton(
        onClick = { goToDisplay() },
        backgroundColor = SonicSilver,
        buttonText = "Display"
    )
    KoreanButton(
        onClick = { goToResults() },
        backgroundColor = CyanCobaltBlue,
        buttonText = "Results"
    )
}

@Composable
fun InitStateScreen() {
    // Handle initialization state if needed
}

@Composable
fun LoadingStateScreen(wordType: SheetsHelper.WordType, percentage: Float) {
    LinearLoadingState(
        wordType = wordType,
        progress = percentage
    )
}

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