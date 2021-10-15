package com.syiyi.reader.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import com.syiyi.reader.ui.theme.Dark
import com.syiyi.reader.ui.theme.Light
import com.syiyi.reader.viewmodel.ThemeViewModel
import com.syiyi.reader.viewmodel.isDarkTheme

class MainActivity : ComponentActivity() {

    private val themeViewModel by viewModels<ThemeViewModel>()

    @ExperimentalAnimationApi
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

