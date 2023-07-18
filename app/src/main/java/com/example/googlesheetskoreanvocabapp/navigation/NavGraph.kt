package com.example.googlesheetskoreanvocabapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.googlesheetskoreanvocabapp.MainScreen
import com.example.googlesheetskoreanvocabapp.numbers.NumbersScreen
import com.example.googlesheetskoreanvocabapp.results.ResultsScreen
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
        composable(route = ScreenDestination.AddVerbsScreen.route) {
            AddVerbScreen()
        }
        composable(route = ScreenDestination.DisplayVerbsScreen.route) {
            DisplayVerbsScreen()
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