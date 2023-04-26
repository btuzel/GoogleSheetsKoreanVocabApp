package com.example.googlesheetskoreanvocabapp.verbs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun VerbsScreen(verbsViewModel: VerbsViewModel = hiltViewModel(), onComplete: () -> Unit) {
    val collectedUiState = verbsViewModel.uiState.collectAsState()
    when (val uiState = collectedUiState.value) {
        is VerbsViewModel.QuizUiState.GetWords -> TestPairComposable(
            englishText = uiState.englishWord,
            answerCorrectText = uiState.wasAnswerCorrect,
            koreanTranslation = uiState.defaultWord,
            koreanTranslationChanged = verbsViewModel::koreanWordChanged,
            checkAnswer = verbsViewModel::checkAnswer,
            setStateToInit = verbsViewModel::setStateToInit,
            onComplete = { onComplete() },
            wordType = SheetsHelper.WordType.VERBS
        )
    }
}
