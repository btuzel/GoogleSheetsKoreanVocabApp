package com.example.googlesheetskoreanvocabapp.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlesheetskoreanvocabapp.data.GetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(private val getResult: GetResult) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SendResults(
            listOf()
        )
    )
    val uiState: StateFlow<SendResults> = _uiState

    init {
        viewModelScope.launch {
            _uiState.value = getResult()?.toList()?.let { SendResults(it) } ?: SendResults(listOf())
        }
    }

    data class SendResults(
        val data: List<String>
    )
}