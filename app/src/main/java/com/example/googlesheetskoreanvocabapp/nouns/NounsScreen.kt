package com.example.googlesheetskoreanvocabapp.nouns

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ui.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NounsScreen(
    nounsViewModel: NounsViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = nounsViewModel.uiState.collectAsState()
    TestPairComposable(
        englishText = collectedUiState.value.englishWord,
        answerCorrectText = collectedUiState.value.wasAnswerCorrect,
        koreanTranslation = collectedUiState.value.defaultWord,
        koreanTranslationChanged = nounsViewModel::koreanWordChanged,
        checkAnswer = nounsViewModel::checkAnswer,
        setStateToInit = nounsViewModel::setStateToInit,
        onComplete = onComplete,
        wordType = SheetsHelper.WordType.NOUNS,
        totalPairs = collectedUiState.value.remainingPairs,
        saveResult = nounsViewModel::saveResult
    )
}


