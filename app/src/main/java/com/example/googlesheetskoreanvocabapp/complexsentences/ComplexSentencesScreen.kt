package com.example.googlesheetskoreanvocabapp.complexsentences

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ui.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@RequiresApi(Build.VERSION_CODES.S)
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
        totalPairs = collectedUiState.value.remainingPairs,
        saveResult = getComplexSentencesViewModel::saveResult
    )
}

