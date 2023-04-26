package com.example.googlesheetskoreanvocabapp.common

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
    val wasAnswerCorrect: AnswerState
)