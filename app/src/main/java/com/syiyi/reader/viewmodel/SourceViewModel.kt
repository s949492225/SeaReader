package com.syiyi.reader.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syiyi.reader.model.Source
import com.syiyi.reader.repository.LocalSourceRepository
import com.syiyi.reader.repository.SourceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SourceViewModel : ViewModel() {

    private var sourceRepository: SourceRepository? = null

    private val mutableListSourceState = MutableStateFlow<List<Source>>(emptyList())
    val listSourceState: StateFlow<List<Source>> = mutableListSourceState


    fun loadSource(context: Context) {
        viewModelScope.launch {
            runCatching {
                sourceRepository(context).list()
            }.onSuccess {
                mutableListSourceState.value = it
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