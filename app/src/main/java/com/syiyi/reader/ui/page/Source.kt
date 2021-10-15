package com.syiyi.reader.ui.page

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.syiyi.reader.model.Source
import com.syiyi.reader.ui.common.ActionBar
import com.syiyi.reader.ui.theme.AppTheme
import com.syiyi.reader.viewmodel.SourceViewModel

@ExperimentalAnimationApi
@Composable
fun Source(sourceViewModel: SourceViewModel) {

    val sourceList by sourceViewModel.listSourceState.collectAsState()
    val context: Context = LocalContext.current
    LaunchedEffect(true) {
        sourceViewModel.loadSource(context)
    }
    Surface(color = AppTheme.colors.surface, modifier = Modifier.fillMaxSize()) {
        Column {
            ActionBar {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                ) {
                    val (title, action) = createRefs()
                    Text(
                        "书源",
                        style = TextStyle(color = AppTheme.colors.titleTextColor, fontSize = 16.sp),
                        modifier = Modifier.constrainAs(title) {
                            centerTo(parent)
                        }
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.constrainAs(action) {
                            end.linkTo(parent.end, margin = 16.dp)
                            centerVerticallyTo(parent)
                        }) {
                        Icon(
                            Icons.Sharp.Search,
                            modifier = Modifier.clickable { },
                            contentDescription = "搜索",
                            tint = AppTheme.colors.titleTextColor
                        )
                    }
                }
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sourceList) { source ->
                    SourceRow(source)
                }
            }
        }

    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun SourceRow(
    source: Source = Source(
        id = 0,
        name = "fdsafdsa",
        key = "fdsa",
        script = "",
        categories = emptyList()
    )
) {
    Column(
        modifier = Modifier
            .background(AppTheme.colors.background, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        var visible by remember { mutableStateOf(false) }

        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                visible = !visible
            })
        {
            Text(
                source.name,
                maxLines = 1,
                style = TextStyle(color = AppTheme.colors.titleTextColor, fontSize = 16.sp),
            )
        }


        AnimatedVisibility(visible = visible) {
            if (!source.categories.isNullOrEmpty()) {
                FlowRow(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    mainAxisAlignment = FlowMainAxisAlignment.Start,
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp
                ) {
                    source.categories.forEach { first ->
                        val isFirst = !first.list.isNullOrEmpty()
                        val tagModifier: Modifier = if (isFirst) {
                            Modifier.fillMaxWidth()
                        } else {
                            Modifier.wrapContentWidth()
                        }

                        if (isFirst) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                            )
                        }

                        Box(
                            modifier = tagModifier
                                .height(30.dp)
                                .border(
                                    1.dp,
                                    AppTheme.colors.secondTitleTextColor,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                first.name,
                                style = TextStyle(
                                    color = AppTheme.colors.secondTitleTextColor,
                                    fontSize = 14.sp
                                )
                            )
                        }

                        first.list?.forEach { second ->
                            Box(
                                modifier = Modifier
                                    .height(30.dp)
                                    .border(
                                        1.dp,
                                        AppTheme.colors.secondTitleTextColor,
                                        shape = RoundedCornerShape(15.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    second.name,
                                    style = TextStyle(
                                        color = AppTheme.colors.secondTitleTextColor,
                                        fontSize = 14.sp
                                    ),
                                    modifier = Modifier.padding(start = 12.dp, end = 12.dp)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}