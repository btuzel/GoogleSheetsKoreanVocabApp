package com.example.googlesheetskoreanvocabapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import com.example.googlesheetskoreanvocabapp.common.ui.LinearLoadingState
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import com.example.googlesheetskoreanvocabapp.navigation.ScreenDestination

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
        goTestWordType = {
            when (it) {
                SheetsHelper.WordType.YUUN -> navHostController.navigate(ScreenDestination.YuunsScreen.route)
                SheetsHelper.WordType.REPEATABLES -> navHostController.navigate(ScreenDestination.RepeatablesScreen.route)
                SheetsHelper.WordType.OLDWORDS -> navHostController.navigate(ScreenDestination.OldWordsScreen.route)
                SheetsHelper.WordType.HYUNGSEOK -> navHostController.navigate(ScreenDestination.HyungseoksScreen.route)
            }
        },
        goToDisplayWordType = {
            when (it) {
                SheetsHelper.WordType.YUUN -> navHostController.navigate(ScreenDestination.DisplayYuunsScreen.route)
                SheetsHelper.WordType.REPEATABLES -> navHostController.navigate(ScreenDestination.DisplayRepeatablesScreen.route)
                SheetsHelper.WordType.OLDWORDS -> navHostController.navigate(ScreenDestination.DisplayOldWordsScreen.route)
                SheetsHelper.WordType.HYUNGSEOK -> navHostController.navigate(ScreenDestination.DisplayHyungseoksScreen.route)
            }
        },
        doLast50 = { navHostController.navigate(ScreenDestination.YuunsScreen.route) },
        goToAddWordType = {
            when (it) {
                SheetsHelper.WordType.YUUN -> navHostController.navigate(ScreenDestination.AddYuunsScreen.route)
                SheetsHelper.WordType.REPEATABLES -> navHostController.navigate(ScreenDestination.AddRepeatablesScreen.route)
                SheetsHelper.WordType.OLDWORDS -> navHostController.navigate(ScreenDestination.AddOldWordsScreen.route)
                SheetsHelper.WordType.HYUNGSEOK -> navHostController.navigate(ScreenDestination.AddHyungseoksScreen.route)
            }
        }
    )
}

@Composable
fun WordManagementScreen(
    goToResults: () -> Unit,
    goToNumbers: (Boolean) -> Unit,
    goToDisplayWordType: (SheetsHelper.WordType) -> Unit,
    goToAddWordType: (SheetsHelper.WordType) -> Unit,
    goTestWordType: (SheetsHelper.WordType) -> Unit,
    clearSharedPref: () -> Unit,
    uiState: SyncViewModel.SyncState,
    doLast50: () -> Unit
) {
    when (uiState) {
        SyncViewModel.SyncState.Done -> DoneStateScreen(
            goToNumbers = goToNumbers,
            goTestWordType = goTestWordType,
            goToAddWordType = goToAddWordType,
            goToDisplayWordType = goToDisplayWordType,
            goToResults = goToResults,
            clearSharedPref = clearSharedPref,
            doLast50 = doLast50
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
    goTestWordType: (SheetsHelper.WordType) -> Unit,
    goToAddWordType: (SheetsHelper.WordType) -> Unit,
    goToDisplayWordType: (SheetsHelper.WordType) -> Unit,
    goToResults: () -> Unit,
    clearSharedPref: () -> Unit,
    doLast50: () -> Unit
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
            goTestWordType = goTestWordType,
            goToAddWordType = goToAddWordType,
            goToDisplayWordType = goToDisplayWordType,
            goToResults = goToResults,
            doLast50 = doLast50
        )

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
    goTestWordType: (SheetsHelper.WordType) -> Unit,
    goToAddWordType: (SheetsHelper.WordType) -> Unit,
    goToDisplayWordType: (SheetsHelper.WordType) -> Unit,
    goToResults: () -> Unit,
    doLast50: () -> Unit
) {
    Button(onClick = {
        goToResults()
    }) {
        Text("Results")
    }

    Row {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
        ) {
            Button(onClick = {
                goTestWordType(SheetsHelper.WordType.REPEATABLES)
            }) {
                Text("Test Repeatables")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                goTestWordType(SheetsHelper.WordType.YUUN)
            }) {
                Text("Test Yuun")
            }
            Button(onClick = {
                goTestWordType(SheetsHelper.WordType.OLDWORDS)
            }) {
                Text("Test Old Words")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                goTestWordType(SheetsHelper.WordType.HYUNGSEOK)
            }) {
                Text("Test Hyungseok")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
        ) {
            Button(onClick = {
                goToAddWordType(SheetsHelper.WordType.REPEATABLES)
            }) {
                Text("Add Repeatables")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                goToAddWordType(SheetsHelper.WordType.YUUN)
            }) {
                Text("Add Yuun")
            }
            Button(onClick = {
                goToAddWordType(SheetsHelper.WordType.OLDWORDS)
            }) {
                Text("Add Old Words")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                goToAddWordType(SheetsHelper.WordType.HYUNGSEOK)
            }) {
                Text("Add Hyungseok")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
        ) {
            Button(onClick = {
                goToDisplayWordType(SheetsHelper.WordType.REPEATABLES)
            }) {
                Text("Display Repeatables")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                goToDisplayWordType(SheetsHelper.WordType.YUUN)
                GlobalClass.globalProperty = true
            }) {
                Text("Display Yuun")
            }
            Button(onClick = {
                goToDisplayWordType(SheetsHelper.WordType.OLDWORDS)
            }) {
                Text("Display Old Words")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                goToDisplayWordType(SheetsHelper.WordType.HYUNGSEOK)
            }) {
                Text("Display Hyungseok")
            }
        }
    }
    Row {
        Button(onClick = {
            GlobalClass.doLast50 = true
            doLast50()
        }) {
            Text("Do Last 50")
        }
    }
}

class GlobalClass {
    companion object {
        var globalProperty: Boolean = false
        var doLast50: Boolean = false
    }
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