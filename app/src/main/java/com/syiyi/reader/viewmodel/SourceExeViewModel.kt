package com.syiyi.reader.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syiyi.reader.model.Book
import com.syiyi.reader.model.Source
import com.syiyi.reader.repository.LocalSourceRepository
import com.syiyi.reader.repository.SourceExeRepository
import com.syiyi.reader.repository.SourceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SourceExeViewModel : ViewModel() {

    private val mutableListBookState = MutableStateFlow<List<Book>>(emptyList())
    val listBookState: StateFlow<List<Book>> = mutableListBookState

    private val sourceExeRepository: SourceExeRepository by lazy { SourceExeRepository }
    private var sourceRepository: SourceRepository? = null
    private var lastJob: Job? = null

    fun search(
        context: Context,
        sourceListState: StateFlow<List<Source>>,
        keyword: String
    ) {
        lastJob?.apply { cancel() }

        lastJob = viewModelScope.launch {
            if (!sourceListState.value.isNullOrEmpty()) {
                runCatching {
                    val script = sourceRepository(context).inflateScript(sourceListState.value[0])
                    sourceExeRepository.search(script, keyword)
                }.onSuccess {
                    mutableListBookState.value = it
                }
            }
        }
    }

    private fun sourceRepository(context: Context): SourceRepository {
        if (sourceRepository == null) {
            sourceRepository = LocalSourceRepository(context.cacheDir)
        }
        return sourceRepository!!
    }

}