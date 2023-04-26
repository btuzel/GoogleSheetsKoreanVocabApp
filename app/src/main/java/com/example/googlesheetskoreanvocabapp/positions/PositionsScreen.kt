package com.example.googlesheetskoreanvocabapp.positions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun PositionsScreen(
    positionsViewModel: PositionsViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = positionsViewModel.uiState.collectAsState()
    when (val uiState = collectedUiState.value) {
        is PositionsViewModel.QuizUiState.GetWords -> TestPairComposable(
            englishText = uiState.englishWord,
            answerCorrectText = uiState.wasAnswerCorrect,
            koreanTranslation = uiState.defaultWord,
            koreanTranslationChanged = positionsViewModel::koreanWordChanged,
            checkAnswer = positionsViewModel::checkAnswer,
            setStateToInit = positionsViewModel::setStateToInit,
            onComplete = onComplete,
            wordType = SheetsHelper.WordType.POSITIONS
        )
    }
}
