package com.example.googlesheetskoreanvocabapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.googlesheetskoreanvocabapp.MainScreen
import com.example.googlesheetskoreanvocabapp.results.ResultsScreen
import com.example.googlesheetskoreanvocabapp.adverbs.AddAdverbsScreen
import com.example.googlesheetskoreanvocabapp.adverbs.AdverbsScreen
import com.example.googlesheetskoreanvocabapp.adverbs.DisplayAdverbsScreen
import com.example.googlesheetskoreanvocabapp.complexsentences.AddComplexSentencesScreen
import com.example.googlesheetskoreanvocabapp.complexsentences.ComplexSentencesScreen
import com.example.googlesheetskoreanvocabapp.complexsentences.DisplaySentencesScreen
import com.example.googlesheetskoreanvocabapp.nouns.AddNounsScreen
import com.example.googlesheetskoreanvocabapp.nouns.DisplayNounsScreen
import com.example.googlesheetskoreanvocabapp.nouns.NounsScreen
import com.example.googlesheetskoreanvocabapp.numbers.NumbersScreen
import com.example.googlesheetskoreanvocabapp.positions.AddPositionsScreen
import com.example.googlesheetskoreanvocabapp.positions.DisplayPositionsScreen
import com.example.googlesheetskoreanvocabapp.positions.PositionsScreen
import com.example.googlesheetskoreanvocabapp.usefulphrases.AddPhrasesScreen
import com.example.googlesheetskoreanvocabapp.usefulphrases.DisplayPhrasesScreen
import com.example.googlesheetskoreanvocabapp.usefulphrases.PhrasesScreen
import com.example.googlesheetskoreanvocabapp.verbs.AddVerbScreen
import com.example.googlesheetskoreanvocabapp.verbs.DisplayVerbsScreen
import com.example.googlesheetskoreanvocabapp.verbs.VerbsScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenDestination.MainScreen.route
    ) {
        composable(route = ScreenDestination.VerbsScreen.route) {
            VerbsScreen(onComplete = { navigateToMainScreen(navHostController) })
        }
        composable(route = ScreenDestination.AdverbsScreen.route) {
            AdverbsScreen(onComplete = { navigateToMainScreen(navHostController) })
        }
        composable(route = ScreenDestination.NounsScreen.route) {
            NounsScreen(onComplete = { navigateToMainScreen(navHostController) })
        }
        composable(route = ScreenDestination.PositionsScreen.route) {
            PositionsScreen(onComplete = { navigateToMainScreen(navHostController) })
        }
        composable(route = ScreenDestination.UsefulPhrasesScreen.route) {
            PhrasesScreen(onComplete = { navigateToMainScreen(navHostController) })
        }
        composable(route = ScreenDestination.ComplexSentencesScreen.route) {
            ComplexSentencesScreen(onComplete = { navigateToMainScreen(navHostController) })
        }
        composable(route = ScreenDestination.AddVerbsScreen.route) {
            AddVerbScreen()
        }
        composable(route = ScreenDestination.AddAdverbsScreen.route) {
            AddAdverbsScreen()
        }
        composable(route = ScreenDestination.AddNounsScreen.route) {
            AddNounsScreen()
        }
        composable(route = ScreenDestination.AddPositionsScreen.route) {
            AddPositionsScreen()
        }
        composable(route = ScreenDestination.AddUsefulPhrasesScreen.route) {
            AddPhrasesScreen()
        }
        composable(route = ScreenDestination.AddComplexSentencesScreen.route) {
            AddComplexSentencesScreen()
        }
        composable(route = ScreenDestination.DisplayVerbsScreen.route) {
            DisplayVerbsScreen()
        }
        composable(route = ScreenDestination.DisplayAdverbsScreen.route) {
            DisplayAdverbsScreen()
        }
        composable(route = ScreenDestination.DisplayNounsScreen.route) {
            DisplayNounsScreen()
        }
        composable(route = ScreenDestination.DisplayPositionsScreen.route) {
            DisplayPositionsScreen()
        }
        composable(route = ScreenDestination.DisplayUsefulPhrasesScreen.route) {
            DisplayPhrasesScreen()
        }
        composable(route = ScreenDestination.DisplayComplexSentencesScreen.route) {
            DisplaySentencesScreen()
        }
        composable(route = ScreenDestination.ResultsScreen.route) {
            ResultsScreen()
        }
        composable(route = ScreenDestination.NumbersScreen.route) {
            NumbersScreen()
        }
        composable(route = ScreenDestination.MainScreen.route) {
            MainScreen(navHostController)
        }
    }
}

fun navigateToMainScreen(navHostController: NavHostController) {
    navHostController.popBackStack()
}