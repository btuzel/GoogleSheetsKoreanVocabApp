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

    object AddVerbsScreen : ScreenDestination(route = "add_verbs")

    object AddAdverbsScreen :
        ScreenDestination(route = "add_adverbs")

    object AddNounsScreen :
        ScreenDestination(route = "add_nouns")


    object AddPositionsScreen :
        ScreenDestination(route = "add_positions")

    object AddUsefulPhrasesScreen :
        ScreenDestination(route = "add_phrases")

    object AddComplexSentencesScreen :
        ScreenDestination(route = "add_complexsentences")
}