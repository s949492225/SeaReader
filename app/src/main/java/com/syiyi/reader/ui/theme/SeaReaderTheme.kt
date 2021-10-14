package com.syiyi.reader.ui.theme

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.syiyi.reader.viewmodel.ThemeViewModel


const val Light = 0
const val Dark = 1

@Composable
fun SeaReaderTheme(
    window: Window? = null,
    themeViewModel: ThemeViewModel = viewModel(),
    content: @Composable () -> Unit
) {

    val theme by themeViewModel.themeState.collectAsState()

    window?.apply {
        //设置为沉浸式
        WindowCompat.setDecorFitsSystemWindows(window, false)
        //状态栏颜色
        WindowInsetsControllerCompat(this, this.decorView).apply {
            isAppearanceLightStatusBars = theme == Light
            isAppearanceLightNavigationBars = theme == Light
        }
        //改变导航栏的颜色
        navigationBarColor = AppTheme.colors.background.toColor()
    }
    //标题栏颜色
    val colors = when (theme) {
        Light -> LightColorPalette
        Dark -> DarkColorPalette
        else -> LightColorPalette
    }

    AppTheme(
        colors = colors,
        content = content
    )
}