package com.example.googlesheetskoreanvocabapp.yuuns

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ui.TestPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun YuunsScreen(
    yuunsViewModel: YuunsViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = yuunsViewModel.uiState.collectAsState()
    TestPairComposable(
        englishText = collectedUiState.value.englishWord,
        answerCorrectText = collectedUiState.value.wasAnswerCorrect,
        koreanTranslation = collectedUiState.value.defaultWord,
        koreanTranslationChanged = yuunsViewModel::koreanWordChanged,
        checkAnswer = yuunsViewModel::checkAnswer,
        setStateToInit = yuunsViewModel::setStateToInit,
        onComplete = { onComplete() },
        wordType = SheetsHelper.WordType.YUUN,
        totalPairs = collectedUiState.value.remainingPairs,
        saveResult = yuunsViewModel::saveResult
    )
}
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HyungseoksScreen(
    hyungseoksViewModel: HyungseoksViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = hyungseoksViewModel.uiState.collectAsState()
    TestPairComposable(
        englishText = collectedUiState.value.englishWord,
        answerCorrectText = collectedUiState.value.wasAnswerCorrect,
        koreanTranslation = collectedUiState.value.defaultWord,
        koreanTranslationChanged = hyungseoksViewModel::koreanWordChanged,
        checkAnswer = hyungseoksViewModel::checkAnswer,
        setStateToInit = hyungseoksViewModel::setStateToInit,
        onComplete = { onComplete() },
        wordType = SheetsHelper.WordType.HYUNGSEOK,
        totalPairs = collectedUiState.value.remainingPairs,
        saveResult = hyungseoksViewModel::saveResult
    )
}
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RepeatablesScreen(
    repeatablesViewModel: RepeatablesViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = repeatablesViewModel.uiState.collectAsState()
    TestPairComposable(
        englishText = collectedUiState.value.englishWord,
        answerCorrectText = collectedUiState.value.wasAnswerCorrect,
        koreanTranslation = collectedUiState.value.defaultWord,
        koreanTranslationChanged = repeatablesViewModel::koreanWordChanged,
        checkAnswer = repeatablesViewModel::checkAnswer,
        setStateToInit = repeatablesViewModel::setStateToInit,
        onComplete = { onComplete() },
        wordType = SheetsHelper.WordType.REPEATABLES,
        totalPairs = collectedUiState.value.remainingPairs,
        saveResult = repeatablesViewModel::saveResult
    )
}
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun OldWordsScreen(
    oldWordsViewModel: OldWordsViewModel = hiltViewModel(),
    onComplete: () -> Unit,
) {
    val collectedUiState = oldWordsViewModel.uiState.collectAsState()
    TestPairComposable(
        englishText = collectedUiState.value.englishWord,
        answerCorrectText = collectedUiState.value.wasAnswerCorrect,
        koreanTranslation = collectedUiState.value.defaultWord,
        koreanTranslationChanged = oldWordsViewModel::koreanWordChanged,
        checkAnswer = oldWordsViewModel::checkAnswer,
        setStateToInit = oldWordsViewModel::setStateToInit,
        onComplete = { onComplete() },
        wordType = SheetsHelper.WordType.OLDWORDS,
        totalPairs = collectedUiState.value.remainingPairs,
        saveResult = oldWordsViewModel::saveResult
    )
}
