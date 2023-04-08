package com.example.googlesheetskoreanvocabapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.googlesheetskoreanvocabapp.MainScreen
import com.example.googlesheetskoreanvocabapp.adverbs.AdverbsScreen
import com.example.googlesheetskoreanvocabapp.complexsentences.ComplexSentencesScreen
import com.example.googlesheetskoreanvocabapp.nouns.NounsScreen
import com.example.googlesheetskoreanvocabapp.positions.PositionsScreen
import com.example.googlesheetskoreanvocabapp.usefulphrases.PhrasesScreen
import com.example.googlesheetskoreanvocabapp.verbs.VerbsScreen

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


        composable(route = ScreenDestination.MainScreen.route) {
            MainScreen(navHostController)
        }
    }

}

fun navigateToMainScreen(navHostController: NavHostController) {
    navHostController.navigate(ScreenDestination.MainScreen.route) {
        popUpTo(ScreenDestination.MainScreen.route) {
            inclusive = true
        }
    }
}