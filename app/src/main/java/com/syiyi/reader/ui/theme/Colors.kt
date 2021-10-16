package com.syiyi.reader.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

class Colors(
    primary: Color = Color.White,
    secondary: Color = Color.White,
    background: Color = Color.White,
    surface: Color = Color(0xFFEDEDED),
    navigation: Color = Color(0xFFF7F7F7),
    titleTextColor: Color = Color.Black,
    secondTitleTextColor: Color = Color(0xD2000000),
    tabSelectColor: Color = Color(0xFF21BD64),
    tabUnSelectColor: Color = Color(0xFF272727),
    theme: Int = 0
) {
    var primary by mutableStateOf(primary, structuralEqualityPolicy())
        internal set
    var secondary by mutableStateOf(secondary, structuralEqualityPolicy())
        internal set
    var background by mutableStateOf(background, structuralEqualityPolicy())
        internal set
    var surface by mutableStateOf(surface, structuralEqualityPolicy())
        internal set
    var navigation by mutableStateOf(navigation, structuralEqualityPolicy())
        internal set
    var titleTextColor by mutableStateOf(titleTextColor, structuralEqualityPolicy())
        internal set
    var secondTitleTextColor by mutableStateOf(secondTitleTextColor, structuralEqualityPolicy())
        internal set
    var tabSelectColor by mutableStateOf(tabSelectColor, structuralEqualityPolicy())
        internal set
    var tabUnSelectColor by mutableStateOf(tabUnSelectColor, structuralEqualityPolicy())
        internal set
    var theme by mutableStateOf(theme, structuralEqualityPolicy())
        internal set

    fun copy(
        primary: Color = this.primary,
        secondary: Color = this.secondary,
        background: Color = this.background,
        surface: Color = this.surface,
        navigation: Color = this.navigation,
        titleTextColor: Color = this.titleTextColor,
        secondTitleTextColor: Color = this.secondTitleTextColor,
        tabSelectColor: Color = this.tabSelectColor,
        tabUnSelectColor: Color = this.tabUnSelectColor,
        theme: Int = this.theme
    ): Colors = Colors(
        primary,
        secondary,
        background,
        surface,
        navigation,
        titleTextColor,
        secondTitleTextColor,
        tabSelectColor,
        tabUnSelectColor,
        theme
    )


}

fun Color.toColor(): Int {
    return android.graphics.Color.argb(
        this.alpha.toInt(),
        this.red.toInt(),
        this.green.toInt(),
        this.blue.toInt()
    )
}

internal fun Colors.updateColorsFrom(other: Colors) {
    primary = other.primary
    secondary = other.secondary
    background = other.background
    surface = other.surface
    titleTextColor = other.titleTextColor
    secondTitleTextColor = other.secondTitleTextColor
    tabSelectColor = other.tabSelectColor
    tabUnSelectColor = other.tabUnSelectColor
    theme = other.theme
}