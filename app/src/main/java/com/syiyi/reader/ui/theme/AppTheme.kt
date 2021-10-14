package com.syiyi.reader.ui.theme

import androidx.compose.runtime.*

@Composable
fun AppTheme(
    colors: Colors = AppTheme.colors,
    content: @Composable () -> Unit
) {
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        content = content
    )
}

object AppTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
}

internal val LocalColors = staticCompositionLocalOf { Colors() }