package com.example.googlesheetskoreanvocabapp.complexsentences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun ComplexSentencesScreen(
    getComplexSentencesViewModel: GetComplexSentencesViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = getComplexSentencesViewModel.uiState.collectAsState()
    TestPairComposable(
        englishText = collectedUiState.value.englishWord,
        answerCorrectText = collectedUiState.value.wasAnswerCorrect,
        koreanTranslation = collectedUiState.value.defaultWord,
        koreanTranslationChanged = getComplexSentencesViewModel::koreanWordChanged,
        checkAnswer = getComplexSentencesViewModel::checkAnswer,
        setStateToInit = getComplexSentencesViewModel::setStateToInit,
        onComplete = onComplete,
        wordType = SheetsHelper.WordType.COMPLEX_SENTENCES,
        totalPairs = collectedUiState.value.remainingPairs
    )
}

