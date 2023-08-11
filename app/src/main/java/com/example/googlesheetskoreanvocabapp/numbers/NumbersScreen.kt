package com.example.googlesheetskoreanvocabapp.numbers

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.state.AnswerState
import com.example.googlesheetskoreanvocabapp.common.ui.AppButton
import com.example.googlesheetskoreanvocabapp.ui.theme.ErrorRed
import com.example.googlesheetskoreanvocabapp.ui.theme.bodyMedium
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NumbersScreen(numbersViewModel: NumbersViewModel = hiltViewModel(), isPureKorean: String) {
    val collectedUiState = numbersViewModel.uiState.collectAsState()
    LaunchedEffect(key1 = Unit) {
        numbersViewModel.isPureKorean = isPureKorean
    }
    TestPairComposable2(
        isPureKorean = isPureKorean,
        englishText = collectedUiState.value.englishNumber,
        answerCorrectText = collectedUiState.value.wasAnswerCorrect,
        koreanTranslation = collectedUiState.value.defaultNumber,
        koreanTranslationChanged = numbersViewModel::koreanWordChanged,
        checkAnswer = numbersViewModel::checkAnswer,
        setStateToInit = numbersViewModel::setStateToInit,
    )
}

@Composable
fun TestPairComposable2(
    isPureKorean: String,
    englishText: Int,
    answerCorrectText: AnswerState,
    koreanTranslation: String,
    koreanTranslationChanged: (String) -> Unit,
    checkAnswer: (String, String, Boolean) -> Unit,
    setStateToInit: () -> Unit,
) {
    val correctAnswerCount = remember {
        mutableStateOf(0)
    }
    val wrongAnswerCount = remember {
        mutableStateOf(0)
    }
    val attempt = remember {
        mutableStateOf("")
    }
    val previousIncorrectAnswer = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 150.dp)
    ) {
        val correctText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(color = Color.White)
            ) {
                append("Correct answer count: ")
            }
            withStyle(
                style = SpanStyle(color = ErrorRed, fontWeight = FontWeight.Bold)
            ) {
                append(correctAnswerCount.value.toString())
            }
        }
        val incorrectText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(color = Color.White)
            ) {
                append("Incorrect answer count: ")
            }
            withStyle(
                style = SpanStyle(color = ErrorRed, fontWeight = FontWeight.Bold)
            ) {
                append(wrongAnswerCount.value.toString())
            }
        }
        Text(
            text = if (isPureKorean == "true") "Pure Korean" else "Sino Korean",
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.body1,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = correctText)
            Spacer(modifier = Modifier.width(32.dp))
            Text(text = incorrectText)
        }
        Text(
            text = englishText.toString(),
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        when (answerCorrectText) {
            is AnswerState.CorrectAnswer -> {
                Text(text = "Correct!", color = Color.Cyan)
                LaunchedEffect(answerCorrectText) {
                    previousIncorrectAnswer.value = ""
                    correctAnswerCount.value++
                    delay(500L)
                    setStateToInit()
                }
            }

            AnswerState.Init -> {
                if (previousIncorrectAnswer.value.isNotEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Incorrect answer, the right answer is ${previousIncorrectAnswer.value}",
                            fontWeight = FontWeight.Bold,
                            color = ErrorRed
                        )
                        Text(
                            text = "Your answer was ${attempt.value}",
                            fontWeight = FontWeight.Bold,
                            color = ErrorRed
                        )
                    }
                }
            }

            is AnswerState.WrongAnswer -> {
                wrongAnswerCount.value++
                previousIncorrectAnswer.value = answerCorrectText.correctAnswer
                setStateToInit()
            }

            AnswerState.Finished ->
                LaunchedEffect(Unit) {
                    scope.launch {
                        Toast.makeText(
                            context,
                            "${wrongAnswerCount.value} incorrect answers.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
        OutlinedTextField(
            value = koreanTranslation,
            onValueChange = koreanTranslationChanged,
            label = { Text("Korean word") },
            modifier = Modifier
                .width(200.dp)
                .padding(bottom = 32.dp)
        )
        AppButton(onClick = {
            checkAnswer(
                englishText.toString(),
                koreanTranslation,
                isPureKorean == "true"
            )
            attempt.value = koreanTranslation
        }, text = "Submit")
    }


}
