package com.example.googlesheetskoreanvocabapp.navigation

sealed class ScreenDestination(val route: String) {

    object VerbsScreen : ScreenDestination(route = "verbs")

    object MainScreen :
        ScreenDestination(route = "main")

    object AddVerbsScreen : ScreenDestination(route = "add_verbs")

    object DisplayVerbsScreen : ScreenDestination(route = "Display_verbs")

    object ResultsScreen :
        ScreenDestination(route = "results")

    object NumbersScreen :
        ScreenDestination(route = "numbers")
}