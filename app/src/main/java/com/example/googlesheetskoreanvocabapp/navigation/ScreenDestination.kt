package com.example.googlesheetskoreanvocabapp.navigation

const val VERB_TYPE = "verbType"
const val NUMBER_TYPE = "numberType"

sealed class ScreenDestination(val route: String) {

    object MainScreen :
        ScreenDestination(route = "main")

    object YuunsScreen : ScreenDestination(route = "yuuns")

    object AddYuunsScreen : ScreenDestination(route = "add_yuuns")

    object DisplayYuunsScreen : ScreenDestination(route = "Display_yuuns")

    object OldWordsScreen : ScreenDestination(route = "oldwords")

    object AddOldWordsScreen : ScreenDestination(route = "add_oldwords")

    object DisplayOldWordsScreen : ScreenDestination(route = "Display_oldwords")

    object RepeatablesScreen : ScreenDestination(route = "repeatables")

    object AddRepeatablesScreen : ScreenDestination(route = "add_repeatables")

    object DisplayRepeatablesScreen : ScreenDestination(route = "Display_repeatables")

    object HyungseoksScreen : ScreenDestination(route = "hyungseoks")

    object AddHyungseoksScreen : ScreenDestination(route = "add_hyungseoks")

    object DisplayHyungseoksScreen : ScreenDestination(route = "Display_hyungseoks")

    object ResultsScreen :
        ScreenDestination(route = "results")

    object NumbersScreen : ScreenDestination(route = "numbers/{$NUMBER_TYPE}") {
        fun passType(type: String): String {
            return this.route.replace(oldValue = "{$NUMBER_TYPE}", newValue = type)
        }
    }
}