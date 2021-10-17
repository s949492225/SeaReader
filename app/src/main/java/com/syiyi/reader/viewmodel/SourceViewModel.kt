package com.syiyi.reader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syiyi.reader.model.Source
import com.syiyi.reader.repository.SourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(var sourceRepository: SourceRepository) : ViewModel() {

    private val mutableListSourceState = MutableStateFlow<List<Source>>(emptyList())
    val listSourceState: StateFlow<List<Source>> = mutableListSourceState

    fun loadSource() {
        viewModelScope.launch {
            runCatching {
                sourceRepository.list()
            }.onSuccess {
                mutableListSourceState.value = it
            }
        }
    }

}