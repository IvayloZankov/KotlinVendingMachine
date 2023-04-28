package com.fosents.kotlinvendingmachine.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Teal500 = Color(0xFF00A595)
val Teal700 = Color(0xFF018786)
val Teal900 = Color(0xFF006564)
val Gold = Color(0xFFFBC02D)

val BackgroundColor: Color
@Composable
get() = if (isSystemInDarkTheme()) Teal900 else Teal200