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

    private val _uiState = MutableStateFlow(
        listOf(
            BaseWordPairViewModel.SaveResultCompleteState(
                SaveResultState("", ""),
                "",
                "",
                "",
                ""
            )
        )
    )
    val uiState: StateFlow<List<BaseWordPairViewModel.SaveResultCompleteState>> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = getResult()
        }
    }
}