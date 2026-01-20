package com.fosents.kotlinvendingmachine.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Teal200 = Color(0xFF03DAC5)
val Teal700 = Color(0xFF018786)
val Teal900 = Color(0xFF006564)
val Gold = Color(0xFFFBC02D)

val BackgroundColor: Color
@Composable
get() = if (isSystemInDarkTheme()) Teal900 else Teal200