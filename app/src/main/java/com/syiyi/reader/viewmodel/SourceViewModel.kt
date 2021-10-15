package com.syiyi.reader.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syiyi.reader.model.Source
import com.syiyi.reader.repository.LocalSourceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class SourceViewModel : ViewModel() {

    private var localSourceRepository: LocalSourceRepository? = null
    private val mutableListSourceState = MutableStateFlow<List<Source>>(emptyList())
    val listSourceState: StateFlow<List<Source>> = mutableListSourceState


    fun search(keyword: String) {

    }

    fun loadSource(context: Context) {
        viewModelScope.launch {
            val list = localSourceRepository(context).list()
            mutableListSourceState.value = list
        }
    }

    private fun localSourceRepository(context: Context): LocalSourceRepository {
        if (localSourceRepository == null) {
            localSourceRepository = LocalSourceRepository(context.cacheDir)
        }
        return localSourceRepository!!
    }
}