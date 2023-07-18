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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
        goToNumbers = { navHostController.navigate(ScreenDestination.NumbersScreen.route) },
        goToResults = { navHostController.navigate(ScreenDestination.ResultsScreen.route) },
        clearSharedPref = syncViewModel::clearSharedPref,
        doVerbs = syncViewModel::setVerbGroup,
        goTestVerb = { navHostController.navigate(ScreenDestination.VerbsScreen.route) },
        goToDisplay = { navHostController.navigate(ScreenDestination.DisplayVerbsScreen.route) },
        goToAdd = { navHostController.navigate(ScreenDestination.AddVerbsScreen.route) }
    )
}

@Composable
fun WordManagementScreen(
    goToResults: () -> Unit,
    goToNumbers: () -> Unit,
    goToDisplay: () -> Unit,
    goToAdd: () -> Unit,
    goTestVerb: () -> Unit,
    doVerbs: (VerbGroupType) -> Unit,
    clearSharedPref: () -> Unit,
    uiState: SyncViewModel.SyncState
) {
    when (uiState) {
        SyncViewModel.SyncState.Done -> DoneStateScreen(
            goToNumbers = goToNumbers,
            goTestVerb = goTestVerb,
            doVerbs = doVerbs,
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
    goToNumbers: () -> Unit,
    goTestVerb: () -> Unit,
    doVerbs: (VerbGroupType) -> Unit,
    goToAdd: () -> Unit,
    goToDisplay: () -> Unit,
    goToResults: () -> Unit,
    clearSharedPref: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RaindropAnimation()

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { goToNumbers() }) {
            Text(text = "SinoKorean numbers")
        }

        TestVerbButtons(
            doVerbs = doVerbs,
            goTestVerb = goTestVerb
        )

        KoreanButtonsGroup(goToAdd = goToAdd, goToDisplay = goToDisplay, goToResults = goToResults)

        Spacer(modifier = Modifier.height(48.dp))

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
    doVerbs: (VerbGroupType) -> Unit,
    goTestVerb: () -> Unit
) {
    Text(
        text = "Test Verb",
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(top = 16.dp)
    )

    Row {
        Button(onClick = {
            doVerbs(VerbGroupType.ALL)
            goTestVerb()
        }) {
            Text("Test Verb all")
        }
        Spacer(modifier = Modifier.width(32.dp))
        Button(onClick = {
            doVerbs(VerbGroupType.OLD)
            goTestVerb()
        }) {
            Text("Test Verb old")
        }
        Spacer(modifier = Modifier.width(32.dp))
        Button(onClick = {
            doVerbs(VerbGroupType.NEW)
            goTestVerb()
        }) {
            Text("Test Verb new")
        }
        Spacer(modifier = Modifier.height(32.dp))
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