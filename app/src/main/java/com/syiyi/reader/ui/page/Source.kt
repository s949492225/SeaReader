package com.syiyi.reader.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.syiyi.reader.ui.theme.AppTheme

@Composable
fun Source() {
    Surface(color = AppTheme.colors.surface) {
        Box(
            Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "书源",
                color = AppTheme.colors.titleTextColor,
                style = MaterialTheme.typography.h3,
            )

        }
    }
}