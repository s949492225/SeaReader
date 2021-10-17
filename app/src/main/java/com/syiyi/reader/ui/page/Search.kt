package com.syiyi.reader.ui.page

import androidx.activity.ComponentActivity
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.accompanist.insets.statusBarsPadding
import com.syiyi.reader.R
import com.syiyi.reader.model.Book
import com.syiyi.reader.ui.theme.AppTheme
import com.syiyi.reader.viewmodel.SourceExeViewModel
import com.syiyi.reader.viewmodel.SourceViewModel

@Composable
fun Search(onBack: () -> Unit) {

    val context: ComponentActivity = LocalContext.current as ComponentActivity
    val sourceViewModel = hiltViewModel<SourceViewModel>(context)

    val sourceExeViewModel: SourceExeViewModel = hiltViewModel()

    val bookList by sourceExeViewModel.listBookState.collectAsState()

    fun search(keyword: String) {
        sourceExeViewModel.search(sourceViewModel.listSourceState, keyword)
    }

    Surface(color = AppTheme.colors.surface, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.background(Color.Transparent)) {
            Spacer(modifier = Modifier.statusBarsPadding())
            SearchBar(
                onChange = {
                    search(it)
                },
                onBack = onBack
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                items(bookList) { book ->
                    BookRow(book)
                }
            }
        }
    }
}

@Preview
@Composable
fun BookRow(
    book: Book = Book(
        id = 0,
        name = "xxxx",
        author = "xxx",
        coverUrl = "http://www.xinhuanet.com/ent/2018-08/27/1123334812_15353470655921n.jpg",
        desc = "放点啥艰苦奋斗健身卡就犯困登记卡就犯困京东科技是风景啊设计看风景跨级啊是可烦给付对价啊设计",
        wordsNumber = "xxx",
        categoryDesc = "xxx",
        newsSection = "xxx",
    )
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            elevation = 1.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(book.coverUrl) {
                    placeholder(R.mipmap.book_place_holder)
                    error(R.mipmap.book_place_holder)
                    fallback(R.mipmap.book_place_holder)
                    scale(Scale.FILL)
                    fadeIn(0.1f)
                },
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .width(96.dp)
                    .height(122.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = book.name ?: "",
                maxLines = 1,
                style = TextStyle(
                    color = AppTheme.colors.titleTextColor,
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "" + book.author,
                maxLines = 1,
                style = TextStyle(
                    color = AppTheme.colors.titleTextColor,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = mutableListOf(book.categoryDesc, book.wordsNumber).joinToString("/"),
                maxLines = 1,
                style = TextStyle(
                    color = AppTheme.colors.secondTitleTextColor,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "" + book.newsSection?.replace("\n", ""),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = AppTheme.colors.secondTitleTextColor,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "" + book.desc?.replace("\n", ""),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = AppTheme.colors.secondTitleTextColor,
                    fontSize = 12.sp
                ),
            )
        }
    }
}

@Composable
fun SearchBar(
    onChange: (String) -> Unit = {},
    onBack: () -> Unit = {},
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
                .background(color = AppTheme.colors.background, shape = RoundedCornerShape(6.dp)),
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
                cursorBrush = SolidColor(AppTheme.colors.tabSelectColor),
                textStyle = TextStyle(fontSize = 14.sp, color = AppTheme.colors.titleTextColor),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (inputState.isEmpty()) {
                            Text(
                                text = "请输入书名",
                                color = AppTheme.colors.secondTitleTextColor,
                            )
                        }
                        innerTextField()
                    }
                }
            )
            if (inputState.isNotEmpty()) {
                Icon(
                    Icons.Filled.HighlightOff,
                    contentDescription = "清除",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(horizontal = 8.dp)
                        .clickable {
                            inputState = ""
                        },
                    tint = AppTheme.colors.titleTextColor,
                )
            }
        }
        Text(
            text = "取消",
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable { onBack() },
            color = AppTheme.colors.titleTextColor,
            style = MaterialTheme.typography.button,
        )
    }
}