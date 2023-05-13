package com.example.googlesheetskoreanvocabapp.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.common.ui.SaveResultState
import com.example.googlesheetskoreanvocabapp.common.viewmodel.BaseWordPairViewModel
import com.example.googlesheetskoreanvocabapp.data.GetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(private val getResult: GetResult) : ViewModel() {

    private val initialUiState = listOf(
        BaseWordPairViewModel.SaveResultCompleteState(
            SaveResultState("", ""),
            "",
            "",
            "",
            listOf()
        )
    )
    val uiState: StateFlow<List<BaseWordPairViewModel.SaveResultCompleteState>> =
        MutableStateFlow(initialUiState)

    init {
        viewModelScope.launch {
            (uiState as MutableStateFlow).value = getResult()
        }
    }
}