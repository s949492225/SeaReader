package com.syiyi.reader.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.statusBarsPadding
import com.syiyi.reader.ui.theme.AppTheme
import com.syiyi.reader.viewmodel.SourceViewModel

@Composable
fun Search(sourceViewModel: SourceViewModel = viewModel()) {
    val scope = rememberCoroutineScope()
    val inputState by remember { mutableStateOf("") }
    Surface(color = AppTheme.colors.surface, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.background(Color.Transparent)) {
            Spacer(modifier = Modifier.statusBarsPadding())
            SearchBar(onChange = {})
        }
    }
}

@Preview
@Composable
fun SearchBar(
    onChange: (String) -> Unit = {}
) {
    var inputState by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .height(35.dp)
                .weight(1F)
                .background(color = AppTheme.colors.background, shape = RoundedCornerShape(3.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Sharp.Search,
                contentDescription = "搜索",
                modifier = Modifier.padding(start = 8.dp),
                tint = AppTheme.colors.titleTextColor
            )
            BasicTextField(
                value = inputState,
                onValueChange = {
                    inputState = it
                    onChange(it)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp)
                    .weight(1F),
                textStyle = TextStyle(fontSize = 14.sp),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (inputState.isEmpty()) {
                            Text(
                                text = "请输入书名",
                                color = AppTheme.colors.titleTextColor,
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
        Text(
            text = "取消",
            modifier = Modifier.padding(start = 16.dp),
            color = AppTheme.colors.titleTextColor,
            style = MaterialTheme.typography.button,
        )
    }
}