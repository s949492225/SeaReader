package com.syiyi.reader.ui

import android.view.Window
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.syiyi.reader.ui.page.Main
import com.syiyi.reader.ui.page.Splash
import com.syiyi.reader.ui.theme.SeaReaderTheme

@Composable
fun App(window: Window? = null) {
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
                        Main()
                    }
                }
            }
        }
    }
}