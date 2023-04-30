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
    TestPairComposable(
        englishText = collectedUiState.value.englishWord,
        answerCorrectText = collectedUiState.value.wasAnswerCorrect,
        koreanTranslation = collectedUiState.value.defaultWord,
        koreanTranslationChanged = positionsViewModel::koreanWordChanged,
        checkAnswer = positionsViewModel::checkAnswer,
        setStateToInit = positionsViewModel::setStateToInit,
        onComplete = onComplete,
        wordType = SheetsHelper.WordType.POSITIONS,
        totalPairs = collectedUiState.value.remainingPairs
    )
}
