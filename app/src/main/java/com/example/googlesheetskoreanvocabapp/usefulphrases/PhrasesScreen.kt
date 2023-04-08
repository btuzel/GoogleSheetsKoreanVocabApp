package com.example.googlesheetskoreanvocabapp.usefulphrases

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay

@Composable
fun PhrasesScreen(
    phrasesViewModel: PhrasesViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = phrasesViewModel.uiState.collectAsState()
    when (val uiState = collectedUiState.value) {
        is PhrasesViewModel.QuizUiState.GetWords -> NounsCheckComposable(
            englishText = uiState.englishWord,
            answerCorrectText = uiState.wasAnswerCorrect,
            koreanTranslation = uiState.defaultWord,
            koreanTranslationChanged = phrasesViewModel::koreanWordChanged,
            checkAnswer = phrasesViewModel::checkAnswer,
            setStateToInit = phrasesViewModel::setStateToInit,
            onComplete = onComplete,
        )
    }
}


@Composable
fun NounsCheckComposable(
    englishText: String,
    answerCorrectText: PhrasesViewModel.AnswerState,
    koreanTranslation: String,
    koreanTranslationChanged: (String) -> Unit,
    checkAnswer: (String, String) -> Unit,
    setStateToInit: () -> Unit,
    onComplete: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = englishText,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        when (answerCorrectText) {
            is PhrasesViewModel.AnswerState.CorrectAnswer -> {
                Text(text = "Your answer was correct!")
                LaunchedEffect(answerCorrectText) {
                    delay(2000L)
                    setStateToInit()
                }
            }
            PhrasesViewModel.AnswerState.Init -> {
                // Display nothing
            }
            is PhrasesViewModel.AnswerState.WrongAnswer -> {
                Text(text = "Incorrect answer, the right answer is ${answerCorrectText.correctAnswer}")
                LaunchedEffect(answerCorrectText) {
                    delay(8000L)
                    setStateToInit()
                }
            }
            PhrasesViewModel.AnswerState.Finished ->
                LaunchedEffect(Unit) {
                    onComplete()
                }
        }
        OutlinedTextField(
            value = koreanTranslation,
            onValueChange = koreanTranslationChanged,
            label = { Text("Korean word") },
            modifier = Modifier.width(200.dp)
        )
        Button(onClick = { checkAnswer(englishText, koreanTranslation) }) {
            Text("Submit")
        }
    }
}

