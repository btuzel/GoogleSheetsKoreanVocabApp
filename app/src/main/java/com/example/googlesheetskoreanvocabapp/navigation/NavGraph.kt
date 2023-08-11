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
import com.example.googlesheetskoreanvocabapp.common.VerbGroupType
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
        composable(
            route = ScreenDestination.VerbsScreen.route,
            arguments = listOf(navArgument(VERB_TYPE) {
                type = NavType.StringType
            })
        ) {
            VerbsScreen(onComplete = { navigateToMainScreen(navHostController) },
                verbGroupType = it.arguments?.getString(VERB_TYPE)?.let { it1 -> stringToEnum(it1) }!!
            )
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

fun stringToEnum(input: String): VerbGroupType? {
    return try {
        VerbGroupType.valueOf(input)
    } catch (e: IllegalArgumentException) {
        null
    }
}

fun navigateToMainScreen(navHostController: NavHostController) {
    navHostController.popBackStack()
}