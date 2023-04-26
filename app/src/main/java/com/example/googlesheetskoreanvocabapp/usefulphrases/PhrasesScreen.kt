package com.example.googlesheetskoreanvocabapp.usefulphrases

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun PhrasesScreen(
    phrasesViewModel: PhrasesViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = phrasesViewModel.uiState.collectAsState()
    TestPairComposable(
        englishText = collectedUiState.value.englishWord,
        answerCorrectText = collectedUiState.value.wasAnswerCorrect,
        koreanTranslation = collectedUiState.value.defaultWord,
        koreanTranslationChanged = phrasesViewModel::koreanWordChanged,
        checkAnswer = phrasesViewModel::checkAnswer,
        setStateToInit = phrasesViewModel::setStateToInit,
        onComplete = onComplete,
        wordType = SheetsHelper.WordType.USEFUL_PHRASES
    )
}
