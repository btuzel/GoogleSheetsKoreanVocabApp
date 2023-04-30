package com.example.googlesheetskoreanvocabapp.adverbs

import com.example.googlesheetskoreanvocabapp.common.BaseWordPairViewModel
import com.example.googlesheetskoreanvocabapp.data.AddWordPair
import com.example.googlesheetskoreanvocabapp.data.DeleteWordPair
import com.example.googlesheetskoreanvocabapp.data.GetWordPair
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdverbsViewModel @Inject constructor(
    getWordPair: GetWordPair,
    addWordPair: AddWordPair,
    deleteWordPair: DeleteWordPair,
) : BaseWordPairViewModel(getWordPair, deleteWordPair, addWordPair, SheetsHelper.WordType.ADVERBS)