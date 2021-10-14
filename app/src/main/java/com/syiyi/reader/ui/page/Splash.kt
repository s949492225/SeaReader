package com.syiyi.reader.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.syiyi.reader.commmon.BackHandler
import com.syiyi.reader.const.SPLASH_SECOND_TITLE
import com.syiyi.reader.const.SPLASH_TITLE
import com.syiyi.reader.ui.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun Splash(onNext: () -> Unit) {


    //3秒后跳转
    val currentOnNext by rememberUpdatedState(onNext)
    LaunchedEffect(true) {
        delay(3000)
        currentOnNext()
    }

    //拦截返回键
    BackHandler()

    Surface(color = AppTheme.colors.surface) {
        Box(
            Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Text(
                    text = SPLASH_TITLE,
                    color = AppTheme.colors.titleTextColor,
                    style = MaterialTheme.typography.h3,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = SPLASH_SECOND_TITLE,
                    color = AppTheme.colors.secondTitleTextColor,
                    modifier = Modifier.padding(top = 60.dp),
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}