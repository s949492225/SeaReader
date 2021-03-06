package com.syiyi.reader.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.syiyi.reader.ui.theme.Dark
import com.syiyi.reader.ui.theme.Light
import com.syiyi.reader.viewmodel.ThemeViewModel
import com.syiyi.reader.viewmodel.isDarkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val themeViewModel by viewModels<ThemeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            configTheme()
            App(window)
        }
    }

    private fun configTheme() {
        val isDark = isDarkTheme(this)
        if (isDark) {
            themeViewModel.configTheme(Dark)
        } else {
            themeViewModel.configTheme(Light)
        }
    }
}

