package com.example.googlesheetskoreanvocabapp.navigation

sealed class ScreenDestination(val route: String) {

    object VerbsScreen : ScreenDestination(route = "verbs")

    object AdverbsScreen :
        ScreenDestination(route = "adverbs")

    object NounsScreen :
        ScreenDestination(route = "nouns")

    object MainScreen :
        ScreenDestination(route = "main")

    object PositionsScreen :
        ScreenDestination(route = "positions")

    object UsefulPhrasesScreen :
        ScreenDestination(route = "phrases")

    object ComplexSentencesScreen :
        ScreenDestination(route = "complexsentences")
}