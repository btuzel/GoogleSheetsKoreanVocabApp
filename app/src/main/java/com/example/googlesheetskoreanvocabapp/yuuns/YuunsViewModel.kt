package com.example.googlesheetskoreanvocabapp.yuuns

import com.example.googlesheetskoreanvocabapp.common.viewmodel.BaseWordPairViewModel
import com.example.googlesheetskoreanvocabapp.data.AddWordPair
import com.example.googlesheetskoreanvocabapp.data.DeleteWordPair
import com.example.googlesheetskoreanvocabapp.data.GetWordPair
import com.example.googlesheetskoreanvocabapp.data.SaveResult
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YuunsViewModel @Inject constructor(
    getWordPair: GetWordPair,
    addWordPair: AddWordPair,
    deleteWordPair: DeleteWordPair, saveResult: SaveResult
) : BaseWordPairViewModel(
    getWordPair = getWordPair,
    deleteWordPair = deleteWordPair,
    addWordPair = addWordPair,
    saveResultUseCase = saveResult,
    wordType = SheetsHelper.WordType.YUUN
)

@HiltViewModel
class RepeatablesViewModel @Inject constructor(
    getWordPair: GetWordPair,
    addWordPair: AddWordPair,
    deleteWordPair: DeleteWordPair, saveResult: SaveResult
) : BaseWordPairViewModel(
    getWordPair = getWordPair,
    deleteWordPair = deleteWordPair,
    addWordPair = addWordPair,
    saveResultUseCase = saveResult,
    wordType = SheetsHelper.WordType.REPEATABLES
)

@HiltViewModel
class OldWordsViewModel @Inject constructor(
    getWordPair: GetWordPair,
    addWordPair: AddWordPair,
    deleteWordPair: DeleteWordPair, saveResult: SaveResult
) : BaseWordPairViewModel(
    getWordPair = getWordPair,
    deleteWordPair = deleteWordPair,
    addWordPair = addWordPair,
    saveResultUseCase = saveResult,
    wordType = SheetsHelper.WordType.OLDWORDS
)

@HiltViewModel
class HyungseoksViewModel @Inject constructor(
    getWordPair: GetWordPair,
    addWordPair: AddWordPair,
    deleteWordPair: DeleteWordPair, saveResult: SaveResult
) : BaseWordPairViewModel(
    getWordPair = getWordPair,
    deleteWordPair = deleteWordPair,
    addWordPair = addWordPair,
    saveResultUseCase = saveResult,
    wordType = SheetsHelper.WordType.HYUNGSEOK
)