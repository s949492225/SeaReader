package com.syiyi.reader.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun ActionBar(
    background: Color = Color.Transparent,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.background(color = background)) {
        Spacer(modifier = Modifier.statusBarsPadding())
        content()
    }
}