package com.syiyi.reader.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.syiyi.reader.ui.common.ActionBar
import com.syiyi.reader.ui.theme.AppTheme

@Composable
fun Shelf(onSearch: () -> Unit = {}) {

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
                        "书架",
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
                            modifier = Modifier.clickable { onSearch() },
                            contentDescription = "搜索",
                            tint = AppTheme.colors.titleTextColor
                        )
                        Icon(
                            Icons.Sharp.MoreVert,
                            contentDescription = "更多",
                            tint = AppTheme.colors.titleTextColor
                        )
                    }
                }
            }
            Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Column {

                    Text(
                        text = "书架",
                        color = AppTheme.colors.titleTextColor,
                        style = MaterialTheme.typography.h3,
                    )
                }

            }
        }
    }
}