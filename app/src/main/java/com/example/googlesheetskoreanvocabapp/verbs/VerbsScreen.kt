package com.example.googlesheetskoreanvocabapp.verbs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.VerbGroupType
import com.example.googlesheetskoreanvocabapp.common.ui.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun VerbsScreen(
    verbsViewModel: VerbsViewModel = hiltViewModel(),
    onComplete: () -> Unit,
    verbGroupType: VerbGroupType
) {
    val collectedUiState = verbsViewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        verbsViewModel.verbGroupType = verbGroupType
    }
    TestPairComposable(
        englishText = collectedUiState.value.englishWord,
        answerCorrectText = collectedUiState.value.wasAnswerCorrect,
        koreanTranslation = collectedUiState.value.defaultWord,
        koreanTranslationChanged = verbsViewModel::koreanWordChanged,
        checkAnswer = verbsViewModel::checkAnswer,
        setStateToInit = verbsViewModel::setStateToInit,
        onComplete = { onComplete() },
        wordType = SheetsHelper.WordType.VERBS,
        totalPairs = collectedUiState.value.remainingPairs,
        saveResult = verbsViewModel::saveResult
    )
}
