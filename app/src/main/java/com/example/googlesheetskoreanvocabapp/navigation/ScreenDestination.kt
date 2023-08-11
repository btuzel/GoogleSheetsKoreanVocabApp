package com.example.googlesheetskoreanvocabapp.navigation

const val VERB_TYPE = "verbType"
const val NUMBER_TYPE = "numberType"

sealed class ScreenDestination(val route: String) {

    object VerbsScreen : ScreenDestination(route = "verbs/{$VERB_TYPE}") {
        fun passType(type: String): String {
            return this.route.replace(oldValue = "{$VERB_TYPE}", newValue = type)
        }
    }

    object MainScreen :
        ScreenDestination(route = "main")

    object AddVerbsScreen : ScreenDestination(route = "add_verbs")

    object DisplayVerbsScreen : ScreenDestination(route = "Display_verbs")

    object ResultsScreen :
        ScreenDestination(route = "results")

    object NumbersScreen : ScreenDestination(route = "numbers/{$NUMBER_TYPE}") {
        fun passType(type: String): String {
            return this.route.replace(oldValue = "{$NUMBER_TYPE}", newValue = type)
        }
    }
}