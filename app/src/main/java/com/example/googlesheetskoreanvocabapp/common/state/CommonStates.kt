package com.example.googlesheetskoreanvocabapp.common.state

sealed class AnswerState {
    data class WrongAnswer(val correctAnswer: String, val answer: Answer = Answer.INCORRECT) :
        AnswerState()

    data class CorrectAnswer(val answer: Answer = Answer.CORRECT) : AnswerState()
    object Init : AnswerState()
    object Finished : AnswerState()
}


sealed class Answer {
    object CORRECT : Answer()
    object INCORRECT : Answer()
}

data class GetWords(
    val englishWord: String,
    val defaultWord: String,
    val wasAnswerCorrect: AnswerState,
    val remainingPairs: Int
)

sealed class DisplayState {
    object Loading : DisplayState()
    data class AllPairs(val allPairs: Pair<List<String>, List<String>>) : DisplayState()
}