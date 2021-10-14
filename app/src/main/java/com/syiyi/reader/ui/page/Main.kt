package com.syiyi.reader.ui.page

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.TravelExplore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsHeight
import com.syiyi.reader.ui.theme.AppTheme

sealed class MainFragmentScreen(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Shelf : MainFragmentScreen("Shelf", Icons.Outlined.AutoStories, "书架")
    object Source : MainFragmentScreen("Source", Icons.Outlined.TravelExplore, "书源")
    object Mine : MainFragmentScreen("Mine", Icons.Filled.Person, "我的")
}

val items = listOf(
    MainFragmentScreen.Shelf,
    MainFragmentScreen.Source,
    MainFragmentScreen.Mine,
)

fun isTabSelected(currentDestination: NavDestination?, screen: MainFragmentScreen): Boolean {
    return currentDestination?.hierarchy?.any { it.route == screen.route } == true
}

@Composable
fun Main() {
    val activity = (LocalContext.current as? Activity)
    BackHandler { activity?.finish() }
    val tabNavController = rememberNavController()
    Surface(color = AppTheme.colors.surface) {
        Column {
            Scaffold(
                modifier = Modifier.weight(1F),
                bottomBar = {
                    BottomNavigation(
                        elevation = 0.dp,
                        backgroundColor = AppTheme.colors.background
                    ) {
                        val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        items.forEach { screen ->
                            BottomNavigationItem(
                                icon = { Icon(screen.icon, contentDescription = screen.label) },
                                label = { Text(screen.label) },
                                selected = isTabSelected(currentDestination, screen),
                                selectedContentColor = AppTheme.colors.tabSelectColor,
                                unselectedContentColor = AppTheme.colors.tabUnSelectColor,
                                onClick = {
                                    tabNavController.navigate(screen.route) {
                                        // Pop up to the start destination of the graph to
                                        // avoid building up a large stack of destinations
                                        // on the back stack as users select items
                                        popUpTo(tabNavController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reSelecting the same item
                                        launchSingleTop = true
                                        // Restore state when reSelecting a previously selected item
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    tabNavController,
                    startDestination = MainFragmentScreen.Shelf.route,
                    Modifier.padding(innerPadding)
                ) {
                    composable(MainFragmentScreen.Shelf.route) { Shelf() }
                    composable(MainFragmentScreen.Mine.route) { Mine() }
                    composable(MainFragmentScreen.Source.route) { Source() }
                }
            }
            Spacer(
                modifier = Modifier
                    .navigationBarsHeight()
                    .background(AppTheme.colors.background)
                    .fillMaxWidth()
            )
        }
    }
}
