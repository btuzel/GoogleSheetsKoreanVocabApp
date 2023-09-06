package com.example.googlesheetskoreanvocabapp.yuuns

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.googlesheetskoreanvocabapp.common.ui.AddPairComposable
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper

@Composable
fun AddYuunScreen(yuunViewModel: YuunsViewModel = hiltViewModel()) {
    val state = yuunViewModel.wordState.collectAsState()
    AddPairComposable(
        addWord = yuunViewModel::addWordPair,
        deleteWord = yuunViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.YUUN,
        wordState = state.value
    )
}

@Composable
fun AddHyungseokScreen(hyungseoksViewModel: HyungseoksViewModel = hiltViewModel()) {
    val state = hyungseoksViewModel.wordState.collectAsState()
    AddPairComposable(
        addWord = hyungseoksViewModel::addWordPair,
        deleteWord = hyungseoksViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.HYUNGSEOK,
        wordState = state.value
    )
}

@Composable
fun AddRepeatableScreen(repeatablesViewModel: RepeatablesViewModel = hiltViewModel()) {
    val state = repeatablesViewModel.wordState.collectAsState()
    AddPairComposable(
        addWord = repeatablesViewModel::addWordPair,
        deleteWord = repeatablesViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.REPEATABLES,
        wordState = state.value
    )
}

@Composable
fun AddOldWordScreen(oldWordsViewModel: OldWordsViewModel = hiltViewModel()) {
    val state = oldWordsViewModel.wordState.collectAsState()
    AddPairComposable(
        addWord = oldWordsViewModel::addWordPair,
        deleteWord = oldWordsViewModel::deleteWordPair,
        wordType = SheetsHelper.WordType.OLDWORDS,
        wordState = state.value
    )
}