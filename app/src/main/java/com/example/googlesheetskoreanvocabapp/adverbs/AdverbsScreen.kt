package com.example.googlesheetskoreanvocabapp.adverbs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ui.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@RequiresApi(Build.VERSION_CODES.S)
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
        wordType = SheetsHelper.WordType.ADVERBS,
        totalPairs = collectedUiState.value.remainingPairs,
        saveResult = adverbsViewModel::saveResult
    )
}
