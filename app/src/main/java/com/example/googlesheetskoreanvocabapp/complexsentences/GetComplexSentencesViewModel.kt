package com.example.googlesheetskoreanvocabapp.complexsentences

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.googlesheetskoreanvocabapp.common.viewmodel.BaseWordPairViewModel
import com.example.googlesheetskoreanvocabapp.data.AddWordPair
import com.example.googlesheetskoreanvocabapp.data.DeleteWordPair
import com.example.googlesheetskoreanvocabapp.data.GetWordPair
import com.example.googlesheetskoreanvocabapp.data.SaveResult
import com.example.googlesheetskoreanvocabapp.data.SheetsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class GetComplexSentencesViewModel @Inject constructor(
    getWordPair: GetWordPair,
    addWordPair: AddWordPair,
    deleteWordPair: DeleteWordPair,
    saveResult: SaveResult
) : BaseWordPairViewModel(
    getWordPair,
    deleteWordPair,
    addWordPair,
    saveResult,
    SheetsHelper.WordType.COMPLEX_SENTENCES
)