package com.example.googlesheetskoreanvocabapp.usefulphrases

import com.example.googlesheetskoreanvocabapp.common.viewmodel.BaseWordPairViewModel
import com.example.googlesheetskoreanvocabapp.data.AddWordPair
import com.example.googlesheetskoreanvocabapp.data.DeleteWordPair
import com.example.googlesheetskoreanvocabapp.data.GetWordPair
import com.example.googlesheetskoreanvocabapp.data.SaveResult
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhrasesViewModel @Inject constructor(
    getWordPair: GetWordPair,
    addWordPair: AddWordPair,
    deleteWordPair: DeleteWordPair,
    saveResult: SaveResult
) : BaseWordPairViewModel(
    getWordPair,
    deleteWordPair,
    addWordPair,
    saveResult,
    SheetsHelper.WordType.USEFUL_PHRASES
)