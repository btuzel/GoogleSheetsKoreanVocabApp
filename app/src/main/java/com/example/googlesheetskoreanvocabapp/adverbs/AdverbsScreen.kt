package com.example.googlesheetskoreanvocabapp.adverbs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AdverbsScreen(
        verbsViewModel: AdverbsViewModel = hiltViewModel(),
        onComplete: () -> Unit,
) {
    val collectedUiState = verbsViewModel.uiState.collectAsState()
    TestPairComposable(
            englishText = collectedUiState.value.englishWord,
            answerCorrectText = collectedUiState.value.wasAnswerCorrect,
            koreanTranslation = collectedUiState.value.defaultWord,
            koreanTranslationChanged = verbsViewModel::koreanWordChanged,
            checkAnswer = verbsViewModel::checkAnswer,
            setStateToInit = verbsViewModel::setStateToInit,
            onComplete = onComplete,
            wordType = SheetsHelper.WordType.ADVERBS
    )
}
