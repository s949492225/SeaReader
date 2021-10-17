package com.syiyi.reader.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syiyi.reader.model.Book
import com.syiyi.reader.model.Source
import com.syiyi.reader.repository.SourceExeRepository
import com.syiyi.reader.repository.SourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceExeViewModel @Inject constructor(
    private val sourceRepository: SourceRepository,
    private val sourceExeRepository: SourceExeRepository
) : ViewModel() {

    private val mutableListBookState = MutableStateFlow<List<Book>>(emptyList())
    val listBookState: StateFlow<List<Book>> = mutableListBookState

    private var lastJob: Job? = null

    fun search(
        sourceListState: StateFlow<List<Source>>,
        keyword: String
    ) {
        lastJob?.apply { cancel() }

        lastJob = viewModelScope.launch {
            if (!sourceListState.value.isNullOrEmpty()) {
                runCatching {
                    val script = sourceRepository.inflateScript(sourceListState.value[0])
                    sourceExeRepository.search(script, keyword)
                }.onSuccess {
                    mutableListBookState.value = it
                }.onFailure {
                    Log.e("SourceExeViewModel", it.message ?: "未知")
                }
            }
        }
    }

}