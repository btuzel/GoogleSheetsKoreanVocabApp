package com.example.googlesheetskoreanvocabapp.navigation

sealed class ScreenDestination(val route: String) {

    object VerbsScreen : ScreenDestination(route = "verbs?limit={limit}/offset={offset}") {
        fun returnVerbsRoute(limit: Int, offset: Int) : String{
            return "verbs?limit=$limit/offset=$offset"
        }
    }

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

    object DisplayVerbsScreen : ScreenDestination(route = "Display_verbs")

    object DisplayAdverbsScreen :
        ScreenDestination(route = "Display_adverbs")

    object DisplayNounsScreen :
        ScreenDestination(route = "Display_nouns")


    object DisplayPositionsScreen :
        ScreenDestination(route = "Display_positions")

    object DisplayUsefulPhrasesScreen :
        ScreenDestination(route = "Display_phrases")

    object DisplayComplexSentencesScreen :
        ScreenDestination(route = "Display_complexsentences")

    object ResultsScreen :
        ScreenDestination(route = "results")

    fun withArgs(vararg args:Int) : String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}