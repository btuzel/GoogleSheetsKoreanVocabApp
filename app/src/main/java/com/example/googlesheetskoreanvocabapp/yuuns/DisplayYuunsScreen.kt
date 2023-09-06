package com.example.googlesheetskoreanvocabapp.yuuns

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.state.DisplayState
import com.example.googlesheetskoreanvocabapp.common.ui.LoadingState
import com.example.googlesheetskoreanvocabapp.common.ui.ShowPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun DisplayYuunsScreen(yuunsViewModel: YuunsViewModel = hiltViewModel()) {
    val allYuuns by yuunsViewModel.displayAllPairsUiState.collectAsState()
    val wordState = yuunsViewModel.wordState.collectAsState()
    when (val value = allYuuns) {
        is DisplayState.AllPairs -> ShowPairComposable(
            pairList = value.allPairs,
            wordType = SheetsHelper.WordType.YUUN,
            onDelete = yuunsViewModel::deleteWordPair,
            wordState = wordState.value
        )
        DisplayState.Loading -> LoadingState(wordType = SheetsHelper.WordType.YUUN)
    }
}

@Composable
fun DisplayHyungseoksScreen(hyungseoksViewModel: HyungseoksViewModel = hiltViewModel()) {
    val allHyungseoks by hyungseoksViewModel.displayAllPairsUiState.collectAsState()
    val wordState = hyungseoksViewModel.wordState.collectAsState()
    when (val value = allHyungseoks) {
        is DisplayState.AllPairs -> ShowPairComposable(
            pairList = value.allPairs,
            wordType = SheetsHelper.WordType.HYUNGSEOK,
            onDelete = hyungseoksViewModel::deleteWordPair,
            wordState = wordState.value
        )
        DisplayState.Loading -> LoadingState(wordType = SheetsHelper.WordType.HYUNGSEOK)
    }
}

@Composable
fun DisplayRepeatablesScreen(repeatablesViewModel: RepeatablesViewModel = hiltViewModel()) {
    val allRepeatables by repeatablesViewModel.displayAllPairsUiState.collectAsState()
    val wordState = repeatablesViewModel.wordState.collectAsState()
    when (val value = allRepeatables) {
        is DisplayState.AllPairs -> ShowPairComposable(
            pairList = value.allPairs,
            wordType = SheetsHelper.WordType.REPEATABLES,
            onDelete = repeatablesViewModel::deleteWordPair,
            wordState = wordState.value
        )
        DisplayState.Loading -> LoadingState(wordType = SheetsHelper.WordType.REPEATABLES)
    }
}

@Composable
fun DisplayOldWordsScreen(oldWordsViewModel: OldWordsViewModel = hiltViewModel()) {
    val allOldWords by oldWordsViewModel.displayAllPairsUiState.collectAsState()
    val wordState = oldWordsViewModel.wordState.collectAsState()
    when (val value = allOldWords) {
        is DisplayState.AllPairs -> ShowPairComposable(
            pairList = value.allPairs,
            wordType = SheetsHelper.WordType.OLDWORDS,
            onDelete = oldWordsViewModel::deleteWordPair,
            wordState = wordState.value
        )
        DisplayState.Loading -> LoadingState(wordType = SheetsHelper.WordType.OLDWORDS)
    }
}
