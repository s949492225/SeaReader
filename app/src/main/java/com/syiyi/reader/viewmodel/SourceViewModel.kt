package com.syiyi.reader.viewmodel

import androidx.lifecycle.ViewModel
import com.syiyi.reader.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SourceViewModel : ViewModel() {

    private val mutableSearchState = MutableStateFlow<List<Book>>(emptyList())
    val searchState: StateFlow<List<Book>> = mutableSearchState


    fun search(keyword: String) {

    }
}