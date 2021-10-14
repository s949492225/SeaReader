package com.syiyi.reader.viewmodel

import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import com.syiyi.reader.ui.theme.Light
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel() {
    private val mutableThemeState = MutableStateFlow(Light)
    val themeState: StateFlow<Int> = mutableThemeState

    fun configTheme(theme: Int) {
        mutableThemeState.value = theme
    }
}

fun isDarkTheme(context: Context): Boolean {
    val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return flag == Configuration.UI_MODE_NIGHT_YES
}