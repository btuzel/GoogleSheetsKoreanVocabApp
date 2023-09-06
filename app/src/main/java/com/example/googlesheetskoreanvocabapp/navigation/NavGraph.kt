package com.example.googlesheetskoreanvocabapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.googlesheetskoreanvocabapp.MainScreen
import com.example.googlesheetskoreanvocabapp.numbers.NumbersScreen
import com.example.googlesheetskoreanvocabapp.results.ResultsScreen
import com.example.googlesheetskoreanvocabapp.yuuns.AddYuunScreen
import com.example.googlesheetskoreanvocabapp.yuuns.AddHyungseokScreen
import com.example.googlesheetskoreanvocabapp.yuuns.AddRepeatableScreen
import com.example.googlesheetskoreanvocabapp.yuuns.AddOldWordScreen
import com.example.googlesheetskoreanvocabapp.yuuns.DisplayYuunsScreen
import com.example.googlesheetskoreanvocabapp.yuuns.DisplayOldWordsScreen
import com.example.googlesheetskoreanvocabapp.yuuns.DisplayRepeatablesScreen
import com.example.googlesheetskoreanvocabapp.yuuns.DisplayHyungseoksScreen
import com.example.googlesheetskoreanvocabapp.yuuns.YuunsScreen
import com.example.googlesheetskoreanvocabapp.yuuns.HyungseoksScreen
import com.example.googlesheetskoreanvocabapp.yuuns.OldWordsScreen
import com.example.googlesheetskoreanvocabapp.yuuns.RepeatablesScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavGraph(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenDestination.MainScreen.route
    ) {

        composable(route = ScreenDestination.YuunsScreen.route) {
            YuunsScreen(onComplete = { navigateToMainScreen(navHostController) })
        }
        composable(route = ScreenDestination.AddYuunsScreen.route) {
            AddYuunScreen()
        }
        composable(route = ScreenDestination.DisplayYuunsScreen.route) {
            DisplayYuunsScreen()
        }

        composable(route = ScreenDestination.RepeatablesScreen.route) {
            RepeatablesScreen(onComplete = { navigateToMainScreen(navHostController) })
        }
        composable(route = ScreenDestination.AddRepeatablesScreen.route) {
            AddRepeatableScreen()
        }
        composable(route = ScreenDestination.DisplayRepeatablesScreen.route) {
            DisplayRepeatablesScreen()
        }

        composable(route = ScreenDestination.OldWordsScreen.route) {
            OldWordsScreen(onComplete = { navigateToMainScreen(navHostController) })
        }
        composable(route = ScreenDestination.AddOldWordsScreen.route) {
            AddOldWordScreen()
        }
        composable(route = ScreenDestination.DisplayOldWordsScreen.route) {
            DisplayOldWordsScreen()
        }

        composable(route = ScreenDestination.HyungseoksScreen.route) {
            HyungseoksScreen(onComplete = { navigateToMainScreen(navHostController) })
        }
        composable(route = ScreenDestination.AddHyungseoksScreen.route) {
            AddHyungseokScreen()
        }
        composable(route = ScreenDestination.DisplayHyungseoksScreen.route) {
            DisplayHyungseoksScreen()
        }

        composable(route = ScreenDestination.ResultsScreen.route) {
            ResultsScreen()
        }
        composable(
            route = ScreenDestination.NumbersScreen.route,
            arguments = listOf(navArgument(NUMBER_TYPE) {
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString(NUMBER_TYPE)?.let { it1 ->
                NumbersScreen(
                    isPureKorean = it1
                )
            }
        }
        composable(route = ScreenDestination.MainScreen.route) {
            MainScreen(navHostController)
        }
    }
}

fun navigateToMainScreen(navHostController: NavHostController) {
    navHostController.popBackStack()
}