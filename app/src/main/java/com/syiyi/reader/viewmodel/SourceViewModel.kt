package com.syiyi.reader.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class SourceViewModel : ViewModel() {

    private val mutableResultState = MutableStateFlow<Any>(0)
    val resultState: StateFlow<Any> = mutableResultState


    fun exe(context: Context) {
    }
}