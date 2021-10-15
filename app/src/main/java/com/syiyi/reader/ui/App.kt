package com.syiyi.reader.ui

import android.content.Context
import android.view.Window
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.syiyi.reader.ui.page.Main
import com.syiyi.reader.ui.page.Search
import com.syiyi.reader.ui.page.Splash
import com.syiyi.reader.ui.theme.SeaReaderTheme
import com.syiyi.reader.viewmodel.SourceViewModel

@ExperimentalAnimationApi
@Composable
fun App(window: Window? = null) {

    val sourceViewModel = viewModel<SourceViewModel>().apply {
        val context: Context = LocalContext.current
        loadSource(context)
    }

    SeaReaderTheme(window) {
        ProvideWindowInsets {
            val navController = rememberNavController()
            Scaffold { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Splash.name,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Splash.name) {
                        Splash(onNext = {
                            navController.navigate(Screen.Main.name) {
                                popUpTo(Screen.Splash.name) { inclusive = true }
                            }
                        })
                    }
                    composable(Screen.Main.name) {
                        Main(sourceViewModel,
                            onSearch = {
                                navController.navigate(Screen.Search.name)
                            })
                    }
                    composable(Screen.Search.name) {
                        Search(onBack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}