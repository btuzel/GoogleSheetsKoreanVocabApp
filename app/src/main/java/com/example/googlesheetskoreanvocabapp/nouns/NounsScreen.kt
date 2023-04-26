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
    TestPairComposable(
            englishText = collectedUiState.value.englishWord,
            answerCorrectText = collectedUiState.value.wasAnswerCorrect,
            koreanTranslation = collectedUiState.value.defaultWord,
            koreanTranslationChanged = nounsViewModel::koreanWordChanged,
            checkAnswer = nounsViewModel::checkAnswer,
            setStateToInit = nounsViewModel::setStateToInit,
            onComplete = onComplete,
            wordType = SheetsHelper.WordType.NOUNS
    )
}


