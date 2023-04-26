package com.example.googlesheetskoreanvocabapp.nouns

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun NounsScreen(
    nounsViewModel: NounsViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = nounsViewModel.uiState.collectAsState()
    when (val uiState = collectedUiState.value) {
        is NounsViewModel.QuizUiState.GetWords -> TestPairComposable(
            englishText = uiState.englishWord,
            answerCorrectText = uiState.wasAnswerCorrect,
            koreanTranslation = uiState.defaultWord,
            koreanTranslationChanged = nounsViewModel::koreanWordChanged,
            checkAnswer = nounsViewModel::checkAnswer,
            setStateToInit = nounsViewModel::setStateToInit,
            onComplete = onComplete,
            wordType = SheetsHelper.WordType.NOUNS
        )
    }
}


