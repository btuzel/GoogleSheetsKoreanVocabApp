package com.example.googlesheetskoreanvocabapp.adverbs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AdverbsScreen(
    adverbsViewModel: AdverbsViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = adverbsViewModel.uiState.collectAsState()
    TestPairComposable(
        englishText = collectedUiState.value.englishWord,
        answerCorrectText = collectedUiState.value.wasAnswerCorrect,
        koreanTranslation = collectedUiState.value.defaultWord,
        koreanTranslationChanged = adverbsViewModel::koreanWordChanged,
        checkAnswer = adverbsViewModel::checkAnswer,
        setStateToInit = adverbsViewModel::setStateToInit,
        onComplete = onComplete,
        wordType = SheetsHelper.WordType.ADVERBS
    )
}
