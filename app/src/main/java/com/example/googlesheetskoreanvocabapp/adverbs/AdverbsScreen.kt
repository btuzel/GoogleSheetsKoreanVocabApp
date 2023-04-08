package com.example.googlesheetskoreanvocabapp.adverbs

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
import androidx.navigation.NavHostController
import com.example.googlesheetskoreanvocabapp.navigation.ScreenDestination
import kotlinx.coroutines.delay

@Composable
fun AdverbsScreen(
    verbsViewModel: AdverbsViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = verbsViewModel.uiState.collectAsState()
    when (val uiState = collectedUiState.value) {
        is AdverbsViewModel.QuizUiState.GetWords -> AdverbsCheckComposable(
            englishText = uiState.englishWord,
            answerCorrectText = uiState.wasAnswerCorrect,
            koreanTranslation = uiState.defaultWord,
            koreanTranslationChanged = verbsViewModel::koreanWordChanged,
            checkAnswer = verbsViewModel::checkAnswer,
            setStateToInit = verbsViewModel::setStateToInit,
            onComplete = onComplete,
        )
    }
}


@Composable
fun AdverbsCheckComposable(
    englishText: String,
    answerCorrectText: AdverbsViewModel.AnswerState,
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
            is AdverbsViewModel.AnswerState.CorrectAnswer -> {
                Text(text = "Your answer was correct!")
                LaunchedEffect(answerCorrectText) {
                    delay(2000L)
                    setStateToInit()
                }
            }
            AdverbsViewModel.AnswerState.Init -> {
                // Display nothing
            }
            is AdverbsViewModel.AnswerState.WrongAnswer -> {
                Text(text = "Incorrect answer, the right answer is ${answerCorrectText.correctAnswer}")
                LaunchedEffect(answerCorrectText) {
                    delay(8000L)
                    setStateToInit()
                }
            }
            AdverbsViewModel.AnswerState.Finished ->
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

