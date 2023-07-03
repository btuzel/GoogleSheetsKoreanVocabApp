package com.example.googlesheetskoreanvocabapp.verbs

import com.example.googlesheetskoreanvocabapp.common.viewmodel.BaseWordPairViewModel
import com.example.googlesheetskoreanvocabapp.data.AddWordPair
import com.example.googlesheetskoreanvocabapp.data.DeleteWordPair
import com.example.googlesheetskoreanvocabapp.data.GetWordPair
import com.example.googlesheetskoreanvocabapp.data.SaveResult
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerbsViewModel @Inject constructor(
    getWordPair: GetWordPair,
    addWordPair: AddWordPair,
    deleteWordPair: DeleteWordPair, saveResult: SaveResult
) : BaseWordPairViewModel(
    getWordPair = getWordPair,
    deleteWordPair = deleteWordPair,
    addWordPair = addWordPair,
    saveResultUseCase = saveResult,
    wordType = SheetsHelper.WordType.VERBS
)